package com.zero.generator.common.util;

import com.zero.generator.common.property.GeneratorProperty;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.net.URL;

/**
 * @author  zero
 */
public class YamlConfigUtil {

    private static GeneratorProperty generatorProperty;

    static {
        URL url = YamlConfigUtil.class.getClassLoader().getResource("bootstrap-dev.yml");
        if (url.getPath().contains("jar")) { // 用户未提供配置文件
            System.err.println("Can not find file named 'bootstrap-dev.yml' at resources path, please make sure that you have defined that file.");
            System.exit(0);
        }
        else {
            InputStream inputStream = YamlConfigUtil.class.getClassLoader().getResourceAsStream("bootstrap-dev.yml");
            Yaml yaml = new Yaml();
            generatorProperty = yaml.loadAs(inputStream, GeneratorProperty.class);
        }
    }

    public static GeneratorProperty getConfiguration() {
        return generatorProperty;
    }

}
