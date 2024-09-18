package com.zero.generator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ZeroGeneratorVelocityApplication {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        SpringApplication.run(ZeroGeneratorVelocityApplication.class, args);
        long end = System.currentTimeMillis();
        System.out.println("ZeroGeneratorVelocityApplication 启动成功耗时:\t " + ( end - start) + "\t ms.");
    }

}
