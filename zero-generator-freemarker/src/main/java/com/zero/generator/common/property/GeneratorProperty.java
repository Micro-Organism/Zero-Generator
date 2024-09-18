package com.zero.generator.common.property;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class GeneratorProperty {

    @Value("${zero.author}")
    private String author;
    @Value("${zero.packageName}")
    private String packageName;
    @Value("${zero.tableName}")
    private String tableName;
    @Value("${zero.filePath}")
    private String filePath;
    @Value("${zero.fileMapperPath}")
    private String fileMapperPath ;
    @Value("${zero.entityType}")
    private String entityType;
    @Value("${zero.superEntityClass}")
    private String superEntityClass;
    @Value("${zero.superMapperClass}")
    private String superMapperClass;
    @Value("${zero.superServiceClass}")
    private String superServiceClass;
    @Value("${zero.superServiceImplClass}")
    private String superServiceImplClass;
    @Value("${zero.superControllerClass}")
    private String superControllerClass;
    @Value("${zero.packageMapperName}")
    private String packageMapperName;

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.driver-class-name}")
    private String driver;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;


    private SpringProperty spring;
    @Data
    public static class SpringProperty {
        private Object datasource;
    }

    private DatasourceProperty datasource;
    @Data
    public static class DatasourceProperty {
        private String url;
        private String driverClassName;
        private String username;
        private String password;
    }

    private ZeroProperty zero;
    @Data
    public static class ZeroProperty {
        private String author;
        private String packageName;
        private String packageMapperName;
        private String tableName;
        private String autoRemovePre;
        private String entityType;
        private String tablePrefix;
        private String filePath;
        private String fileMapperPath;
        private String superEntityClass;
        private String superControllerClass;
        private String superServiceClass;
        private String superServiceImplClass;
        private String superMapperClass;
        private String fileOverride;
        private String restful;
        private String swagger;
        private String lombok;
        private String logicDelete;
        private String optimisticLock;
        private String columnConstant;
        private String columnMapping;
        private String superEntityColumns;
    }

    /**
     * 将带 = 号的字符串转换为对象
     * @param input 字符串
     * @param clazz 对象类型
     * @return  对象
     * @param <T>   对象类型
     */
    public static <T> T convertStringToObject(String input, Class<T> clazz) {
        try {
            // 创建对象实例
            T obj = (T) clazz.getDeclaredConstructor().newInstance();
            // 将字符串按逗号分割
            String[] pairs = input.replace("{", "").replace("}", "").split(",");
            // 遍历每个键值对
            for (String pair : pairs) {
                // 按等号分割键和值 限制分割次数为2
                String[] keyValue = pair.split("=", 2);
                if (keyValue.length == 2) {
                    String key = keyValue[0].trim();
                    String value = keyValue[1].trim();

                    // 使用反射设置对象的属性
                    Field field = clazz.getDeclaredField(toCamelCase(key));
                    field.setAccessible(true);

                    // 根据字段类型转换值
                    if (field.getType() == int.class || field.getType() == Integer.class) {
                        field.set(obj, Integer.parseInt(value));
                    }
                    else if (field.getType() == String.class) {
                        field.set(obj, value);
                    }
                }
            }
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 方法：将中划线分隔的字符串转换为小驼峰命名
     * @param input 中划线分隔的字符串
     * @return  小驼峰命名
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

//    public void initDatasource() {
//        //获取datasource
////        Object datasource = ConfigUtil.getConfiguration().getSpring().getDatasource();
////        ObjectMapper mapper = new ObjectMapper();
////        Map<String, String> map = mapper.readValue(datasource.toString(), Map.class);
//
//        Map<String, Object> map = convertStringToObject(this.getSpring().getDatasource().toString(), Map.class);
//        this.getDatasource().setUrl((String) map.get("url"));
//        this.getDatasource().setUsername((String) map.get("username"));
//        this.getDatasource().setPassword((String) map.get("password"));
//        this.getDatasource().setDriverClassName((String) map.get("driver-class-name"));
//    }
}
