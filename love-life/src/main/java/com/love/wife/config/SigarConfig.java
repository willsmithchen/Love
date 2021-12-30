package com.love.wife.config;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.hyperic.jni.ArchNotSupportedException;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarLoader;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @author lujia chen
 * @version 1.0
 * @date 2020/10/28 21:47
 */

@Slf4j
@Configuration
public class SigarConfig {
    static {
        initSigar();
    }

    /**
     * 初始化sigar的配置文件
     */
    @SneakyThrows
    public static void initSigar() {
        SigarLoader loader = new SigarLoader(Sigar.class);
        String lib = null;
        try {
            lib = loader.getLibraryName();
            log.info("init sigar so 文件==================" + lib);
        } catch (ArchNotSupportedException e) {
            e.printStackTrace();
        }
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:/sigar.so/" + lib);
        if (resource.exists()) {
            InputStream inputStream = resource.getInputStream();
            File tempDir = new File("./log");
            if (!tempDir.exists()) {
                tempDir.mkdirs();
            }
            BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(new File(tempDir, lib), false));
            int length = 0;
            while ((length = inputStream.read()) != -1) {
                os.write(length);
            }
            inputStream.close();
            os.close();
            System.setProperty("org.hyperic.sigar.path", tempDir.getCanonicalPath());
        }
    }
}
