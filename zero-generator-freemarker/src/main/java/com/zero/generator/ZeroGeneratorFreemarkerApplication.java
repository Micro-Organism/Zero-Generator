package com.zero.generator;

import com.zero.generator.common.util.GeneratorUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class ZeroGeneratorFreemarkerApplication {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        SpringApplication.run(ZeroGeneratorFreemarkerApplication.class, args);
        long end = System.currentTimeMillis();
        System.out.println("ZeroGeneratorFreemarkerApplication 启动成功耗时:\t " + ( end - start) + "\t ms.");
    }

}
