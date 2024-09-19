package com.zero.generator.util;

import org.apache.commons.lang3.StringUtils;

public class ZeroStringUtils {

    /**
     * 方法：将中划线分隔的字符串转换为小驼峰命名
     *
     * @param input 中划线分隔的字符串
     * @return 小驼峰命名
     */
    public static String toCamelCase(String input) {
        // 按中划线分割字符串
        String[] parts = input.split("-");

        // 构建 StringBuilder 来存储结果
        StringBuilder camelCaseString = new StringBuilder();

        // 遍历数组
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];

            // 第一个单词保持小写
            if (i == 0) {
                camelCaseString.append(part.toLowerCase());
            } else {
                // 其余单词首字母大写，其他字母小写
                camelCaseString.append(part.substring(0, 1).toUpperCase())
                        .append(part.substring(1).toLowerCase());
            }
        }

        return camelCaseString.toString();
    }

    /**
     * 方法：将字符串命名的字符串转换为下划线分割的字符串
     *
     * @param input 命名的字符串
     * @return 下划线分割的字符串
     */
    public static String toUnderScoreCase(String input) {
        if (StringUtils.isBlank(input)) {
            return input; // 如果输入为空或 null，直接返回
        }

        // 正则表达式用于在大写字母前添加下划线
        String result = input.replaceAll("([a-z])([A-Z])", "$1_$2");

        // 将整个字符串转换为小写
        return result.toLowerCase();
    }

    /**
     * 是否包含字符串
     *
     * @param str  验证字符串
     * @param strs 字符串组
     * @return 包含返回true
     */
    public static boolean inStringIgnoreCase(String str, String... strs) {
        if (str != null && strs != null) {
            for (String s : strs) {
                if (str.equalsIgnoreCase(trim(s))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 去空格
     */
    public static String trim(String str) {
        return (str == null ? "" : str.trim());
    }

}
