package com.zero.generator.execute;

import com.zero.generator.common.util.GeneratorUtil;

/**
 * 通用代码生成器
 */
public class GeneralGenerator {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        GeneratorUtil.getInstance().generateGeneral();
        long end = System.currentTimeMillis();
        System.out.println("ZeroGeneratorFreemarkerApplication 启动成功耗时:\t " + ( end - start) + "\t ms.");
    }

}
