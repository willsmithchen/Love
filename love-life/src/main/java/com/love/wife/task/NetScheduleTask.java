package com.love.wife.task;

import cn.hutool.core.date.SystemClock;
import com.love.wife.model.vo.NetVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * @author lujia chen
 * @version 1.0
 * @date 2020/10/30 15:06
 * <p>
 * 网络net流量定时任务
 */

@Slf4j
@Data
@Configuration
@EnableScheduling
@ApiModel(value = "网络流量定时任务")
@ConfigurationProperties(prefix = "net.monitor")
public class NetScheduleTask {
    @ApiModelProperty(value = "是否监控")
    private Boolean monitorSwitch;
    @ApiModelProperty(value = "定时表达式")
    private String cron;

    @Resource
    private RedisTemplate redisTemplate;

    @Scheduled(cron = "${net.monitor.cron}")
    private void task() {
        if (isLocal()) {
            //此处简单实现不执行
            log.info("window环境，停止定时任务....");
//            return;
        }
        if (monitorSwitch) {
            long start = SystemClock.now();
            log.info("网络net流量定时任务开始");
            run();
            log.info("网络net流量定时任务结束，耗时：{}ms....", (SystemClock.now() - start));
        }
    }

    private void run() {
        Date now = new Date();
        //获取当前网络时间下的网络状态
        NetVo net = null;
        try {
            net = net();
        } catch (SigarException e) {
//            e.printStackTrace();
            log.error("定时任务异常:{}", e.getMessage());
            return;
        }
        net.setCurrentTime(now);
        //去除redis数据
        Long size = redisTemplate.opsForList().size("redis的key");
        if (Objects.isNull(size) || 0 == size) {
            //说明list为空
            net.setCurrentRxPackets(0L);
            net.setCurrentTxPackets(0L);
            net.setRxBytes(0L);
            net.setTxBytes(0L);
            redisTemplate.opsForList().leftPush("redis的key", net);
            return;
        }
        //小于限制长度时，去除最后一个，计算本次取数。
        Object index = redisTemplate.opsForList().index("redis的key", 0L);
        NetVo netVo = (NetVo) index;
        net.setCurrentRxPackets(net.getRxPackets() - netVo.getRxPackets());
        net.setCurrentTxPackets(net.getTxPackets() - netVo.getTxPackets());
        net.setCurrentRxBytes(net.getRxBytes() - netVo.getRxPackets());
        net.setCurrentTxBytes(net.getTxBytes() - netVo.getTxBytes());
        redisTemplate.opsForList().leftPush("redis的key", net);
        //长度限制
        if (size >= 3000) {
            redisTemplate.opsForList().trim("redis的key", 0L, (3000 - 1));

        }
    }

    /**
     * 主机网络信息
     */
    private NetVo net() throws SigarException {
        NetVo netVo = new NetVo();
        long rxPackets = 0L, txPackets = 0L, rxBytes = 0L, txBytes = 0L;
        Sigar sigar = new Sigar();
        String[] ifNames = sigar.getNetInterfaceList();
        for (int i = 0; i < ifNames.length; i++) {
            String name = ifNames[i];
            NetInterfaceConfig ifconfig = sigar.getNetInterfaceConfig(name);
            if ((ifconfig.getFlags() & 1L) <= 0L) {
                continue;
            }
            NetInterfaceStat ifstat = sigar.getNetInterfaceStat(name);
            //接收的总包裹数
            rxPackets += ifstat.getRxPackets();
            //发送的总包裹数
            txPackets += ifstat.getTxPackets();
            //接收到的总字节数
            rxBytes += ifstat.getRxBytes();
            //发送的总字节数
            txBytes += ifstat.getTxBytes();
        }
        netVo.setRxPackets(rxPackets);
        netVo.setTxPackets(txPackets);
        netVo.setRxBytes(rxBytes);
        netVo.setTxBytes(txBytes);
        return netVo;
    }

    /**
     * 是否window运行，true代表window
     */
    public static boolean isLocal() {
        return System.getProperties().getProperty("os.name").toUpperCase().contains("WINDOWS");
    }
}
