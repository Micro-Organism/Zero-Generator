package com.zero.generator;

import com.zero.generator.common.property.GeneratorProperty;
import com.zero.generator.common.util.YamlConfigUtil;
import com.zero.generator.service.GeneratorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.zero.generator.common.property.GeneratorProperty.convertStringToObject;

@SpringBootTest
class ZeroGeneratorFreemarkerApplicationTests {

    @Autowired
    private GeneratorProperty generatorProperty;

    @Autowired
    private GeneratorService generatorService;

    @Test
    void contextLoads() {
        //        GeneratorUtil.getInstance().generatorMySQL();
        GeneratorProperty.DatasourceProperty datasourceProperty = convertStringToObject(YamlConfigUtil.getConfiguration().getSpring().getDatasource().toString(), GeneratorProperty.DatasourceProperty.class);
        System.out.println(datasourceProperty.getUsername() + "\t"+ datasourceProperty.getPassword());
//        System.out.println(ConfigUtil.getConfiguration().getZero().getTablePrefix());
    }

}
