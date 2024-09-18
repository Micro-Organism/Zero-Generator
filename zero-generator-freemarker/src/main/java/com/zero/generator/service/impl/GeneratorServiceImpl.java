package com.zero.generator.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.zero.generator.common.constant.GeneratorConst;
import com.zero.generator.common.property.GeneratorProperty;
import com.zero.generator.service.GeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GeneratorServiceImpl implements GeneratorService {

    private GeneratorProperty generatorProperty;

    @Autowired
    public GeneratorServiceImpl(GeneratorProperty generatorProperty) {
        this.generatorProperty = generatorProperty;
    }

    @Override
    public void generateGeneral() {
        System.out.println("General Generator Start...");
    }

    @Override
    public void generateMySQL() {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        final String packNameStr = generatorProperty.getPackageMapperName().replace(".", "/");
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + generatorProperty.getFilePath());
        gc.setAuthor(generatorProperty.getAuthor());
        gc.setOpen(false);
        // XML ResultMap
        gc.setBaseResultMap(true);
        // XML columList
        gc.setBaseColumnList(true);
        //是否覆盖文件
        gc.setFileOverride(false);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(generatorProperty.getUrl());
        // dsc.setSchemaName("public");
        dsc.setDriverName(generatorProperty.getDriver());
        dsc.setUsername(generatorProperty.getUsername());
        dsc.setPassword(generatorProperty.getPassword());
        dsc.setTypeConvert(new MySqlTypeConvert() {
            @Override
            public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                if ("datetime".equals(fieldType.toLowerCase())) {
                    return DbColumnType.DATE;
                }
                return super.processTypeConvert(globalConfig, fieldType);
            }
        });
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
//        pc.setModuleName(scanner("模块名"));
        pc.setParent(generatorProperty.getPackageName());
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("pageDto", generatorProperty.getPackageName());
                map.put("baseIdStr", "#{baseId}");
                map.put("baseIdsStr", "#{baseIds}");
                map.put("item", "#{item}");
                map.put("SymbolStr", "#");
                //setMap进去
                this.setMap(map);
            }
        };

        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig(GeneratorConst.MAPPER_TEMPLATE_PATH) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + generatorProperty.getFileMapperPath() + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        focList.add(new FileOutConfig(GeneratorConst.ENTITY_DTO_TEMPLATE_PATH) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + generatorProperty.getFilePath()+ "/" + packNameStr + "/dto" + "/" + tableInfo.getEntityName() + "Dto" + StringPool.DOT_JAVA;
            }
        });
        focList.add(new FileOutConfig(GeneratorConst.ENTITY_ADD_DTO_TEMPLATE_PATH) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + generatorProperty.getFilePath() + "/" + packNameStr + "/dto" + "/" + tableInfo.getEntityName() + "AddDto" + StringPool.DOT_JAVA;
            }
        });
        focList.add(new FileOutConfig(GeneratorConst.ENTITY_MODIFY_DTO_TEMPLATE_PATH) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + generatorProperty.getFilePath() + "/" + packNameStr + "/dto" + "/" + tableInfo.getEntityName() + "ModifyDto" + StringPool.DOT_JAVA;
            }
        });
        focList.add(new FileOutConfig(GeneratorConst.ENTITY_LIST_DTO_TEMPLATE_PATH) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + generatorProperty.getFilePath() + "/" + packNameStr + "/dto" + "/" + tableInfo.getEntityName() + "ListDto" + StringPool.DOT_JAVA;
            }
        });
        focList.add(new FileOutConfig(GeneratorConst.ENTITY_VO_TEMPLATE_PATH) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + generatorProperty.getFilePath() + "/" + packNameStr + "/vo" + "/" + tableInfo.getEntityName() + "Vo" + StringPool.DOT_JAVA;
            }
        });
        focList.add(new FileOutConfig(GeneratorConst.ENTITY_LIST_VO_TEMPLATE_PATH) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + generatorProperty.getFilePath() + "/" + packNameStr + "/vo" + "/" + tableInfo.getEntityName() + "ListVo" + StringPool.DOT_JAVA;
            }
        });
        focList.add(new FileOutConfig(GeneratorConst.ENTITY_DETAIL_TEMPLATE_PATH) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + generatorProperty.getFilePath() + "/" + packNameStr + "/vo" + "/" + tableInfo.getEntityName() + "Detail" + StringPool.DOT_JAVA;
            }
        });

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        mpg.setTemplate(new TemplateConfig().setXml(null));

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        //表名生成策略  下划线转驼峰
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //自动填充设置
        //strategy.setTableFillList();
        //配置是否启用restController
        strategy.setRestControllerStyle(true);
        // 需要生成的表名
        strategy.setEntityLombokModel(true);
        // 自定义 实体 父类 "cn.zero.framework.core.base.entity.BaseEntity"
        strategy.setSuperEntityClass(generatorProperty.getSuperEntityClass());
        // 自定义 controller 父类 "cn.zero.framework.core.base.controller.WebController"
        strategy.setSuperControllerClass(generatorProperty.getSuperControllerClass());
        // 自定义 mapper 父类 "cn.zero.framework.core.base.mapper.SuperMapper"
        strategy.setSuperMapperClass(generatorProperty.getSuperMapperClass());
        // 自定义 service 父类 "cn.zero.framework.core.base.service.BaseService"
        strategy.setSuperServiceClass(generatorProperty.getSuperServiceClass());
        // 自定义 serviceImpl 父类 "cn.zero.framework.core.base.service.iml.BaseServiceImpl"
        strategy.setSuperServiceImplClass(generatorProperty.getSuperServiceImplClass());

        strategy.setInclude(generatorProperty.getTableName());
        strategy.setSuperEntityColumns("id");
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        // 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        // 放置自己项目的 src/main/resources/template 目录下, 默认名称一下可以不配置，也可以自定义模板名称
//        TemplateConfig tc = new TemplateConfig();
//        tc.setEntity("tpl/entity.java.vm");
//        tc.setMapper("tpl/mapper.java.vm");
//        tc.setXml("tpl/mapper.xml.vm");
//        tc.setService("tpl/service.java.vm");
//        tc.setController("tpl/controller.java.vm");
//        mpg.setTemplate(tc);
//        ConfigBuilder config = mpg.getConfig();
//        if(config == null){
//            DataSourceConfig dataSource = mpg.getDataSource();
//            dataSource.setDbType(DbType.POSTGRE_SQL);
//            ConfigBuilder configBuilder =new ConfigBuilder(mpg.getPackageInfo(), dataSource, mpg.getStrategy(), mpg.getTemplate(), mpg.getGlobalConfig());
//            mpg.setConfig(configBuilder);
//            InjectionConfig injectionConfig = configBuilder.getInjectionConfig();
//            if(injectionConfig != null){
//                configBuilder.getInjectionConfig().setConfig(configBuilder);
//            }
//        }

        mpg.execute();
    }
}
