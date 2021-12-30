package com.love.wife.web;

import com.love.wife.model.vo.*;
import com.lujia.model.Outcome;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.hyperic.sigar.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lujia chen
 * @version 1.0
 * @date 2020/10/28 21:45
 */

@Api(tags = "linux管理")
@RequestMapping(value = "/linux-manager")
@RestController
public class LinuxController {

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 监控信息
     */
    @ApiOperation(value = "监控信息", notes = "监控信息")
    @GetMapping(value = "/minitor-info")
    public Outcome<MonitorVo> info() {
        MonitorVo monitorVo = new MonitorVo();
        Sigar sigar = new Sigar();
        monitorVo.setCpu(cpuPerc(sigar));
        monitorVo.setMemory(memory(sigar));
        monitorVo.setDisk(file(sigar));
        List<NetVo> list = redisTemplate.opsForList().range("redis的key", 0L, (3000 - 1));
        monitorVo.setNetVoList(list);
        sigar.close();
        return Outcome.success().setData(monitorVo);
    }

    /**
     * 内存
     */
    @SneakyThrows
    public MemoryVo memory(Sigar sigar) {
        MemoryVo memoryVo = new MemoryVo();
        //使用sigar获取内存
        Mem mem = sigar.getMem();
        long total = mem.getTotal();
        long ram = mem.getRam();
        long actualUsed = mem.getActualUsed();
        long actualFree = mem.getActualFree();
        double usedPercent = mem.getUsedPercent();
        memoryVo.setMemory(String.format("%d", total / 1024 / 1024 / 1024) + "GB");
        memoryVo.setMemRam(String.format("%d", ram / 1024) + "GB");
        memoryVo.setMemUsed(String.format("%d", actualUsed / 1024 / 1024 / 1024) + "GB");
        memoryVo.setMemFrees(String.format("%d", actualFree / 1024 / 1024 / 1024) + "GB");
        memoryVo.setMemoryUsage(String.format("%.2f", usedPercent) + "%");
        return memoryVo;
    }

    /**
     * cpu使用率
     */
    @SneakyThrows
    private static CpuVo cpuPerc(Sigar sigar) {
        CpuPerc cpuPerc = sigar.getCpuPerc();
        CpuVo cpuVo = new CpuVo();
        cpuVo.setUser(CpuPerc.format(cpuPerc.getUser()));
        cpuVo.setSys(CpuPerc.format(cpuPerc.getSys()));
        cpuVo.setWait(CpuPerc.format(cpuPerc.getWait()));
        cpuVo.setNice(CpuPerc.format(cpuPerc.getNice()));
        cpuVo.setIdle(CpuPerc.format(cpuPerc.getIdle()));
        cpuVo.setCombined(CpuPerc.format(cpuPerc.getCombined()));
        return cpuVo;
    }

    /**
     * 磁盘
     */
    @SneakyThrows
    private DiskVo file(Sigar sigar) {
        DiskVo diskVo = new DiskVo();
        long total = 0L;
        long free = 0L;
        long avail = 0L;
        long used = 0L;
        FileSystem[] fileSystemList = sigar.getFileSystemList();
        for (int i = 0; i < fileSystemList.length; i++) {
            FileSystem fs = fileSystemList[i];
            FileSystemUsage usage = null;
            usage = sigar.getFileSystemUsage(fs.getDirName());
            switch (fs.getType()) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    //文件系统总大小
                    total = usage.getTotal();
                    //文件系统剩余大小
                    free = usage.getFree();
                    //文件系统可用大小
                    avail = usage.getAvail();
                    //文件系统已经使用量
                    used += usage.getUsed();
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                default:
                    break;
            }
        }
        diskVo.setTotal(String.format("%d", total / 1024 / 1024) + "GB");
        diskVo.setFree(String.format("%d", free / 1024 / 1024) + "GB");
        diskVo.setAvail(String.format("%d", avail / 1024 / 1024) + "GB");
        diskVo.setUsed(String.format("%d", used / 1024 / 1024) + "GB");
        diskVo.setUsePercent(String.format("%d", used / total) + "%");
        return diskVo;
    }


}
