package com.zero.generator.execute;

import com.zero.generator.common.util.GeneratorUtil;
import lombok.SneakyThrows;

/**
 * MySQL 代码生成器
 */
public class MySQLGenerator {

    @SneakyThrows
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        GeneratorUtil.getInstance().generatorMySQL();
        long end = System.currentTimeMillis();
        System.out.println("ZeroGeneratorFreemarkerApplication 启动成功耗时:\t " + ( end - start) + "\t ms.");
    }

}
