package com.zero.generator.common.util;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
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

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.zero.generator.common.property.GeneratorProperty.convertStringToObject;

@Service
public class GeneratorUtil {

    private final GeneratorProperty.DatasourceProperty datasourceProperty;
    private final GeneratorProperty.ZeroProperty zeroProperty;

    /**
     *  私有构造函数，防止外部实例化
     */
    private GeneratorUtil() {
        // 初始化操作
        datasourceProperty = convertStringToObject(YamlConfigUtil.getConfiguration().getSpring().getDatasource().toString(), GeneratorProperty.DatasourceProperty.class);
        zeroProperty = YamlConfigUtil.getConfiguration().getZero();
    }

    /**
     * 生成通用代码
     */
    public void generateGeneral() {
        // System.getProperty("user.dir") // get current project dir
        String projectDir = System.getProperty("user.dir");
        String outputDir = projectDir + zeroProperty.getFilePath();
        // String outputDir = System.getProperty("user.dir") + "/src/main/java";
        // table name, Pay attention to capitalization
        String[] tableNames = new String[]{ zeroProperty.getTableName() };
        // database url
        String url = datasourceProperty.getUrl(); //"jdbc:mysql://localhost:3306/zero?useUnicode=true&characterEncoding=utf8";
        String userName = datasourceProperty.getUsername(); //"root";
        String password = datasourceProperty.getPassword(); //"root@123";
        // parentPackage
        String parentPackage = zeroProperty.getPackageName(); //"com.zero.generator";
        // need to remove prefix from tablename
        String prefixTable = zeroProperty.getTablePrefix(); //"t_";


        // ===============  Global setting ==================
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(outputDir)
                .setActiveRecord(true)                                //  enable AR,
                .setAuthor(zeroProperty.getAuthor())                            // set Author name
                .setFileOverride(true)                                // enable FileOverride？
                .setIdType(IdType.AUTO)                                //primary strategy
                .setBaseResultMap(true)                            // SQL mappingg
                .setBaseColumnList(true)                            // SQL BaseColumn
                .setServiceName("%sService")                        // service name
                .setOpen(false);

        // =================  database setting   ===============
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL).setDriverName(datasourceProperty.getDriverClassName());
        dsc.setUrl(url).setUsername(userName).setPassword(password);

        // =================  package setting  ===================
        PackageConfig pc = new PackageConfig();
        pc.setParent(parentPackage)                            // parentPackage path
//           .setModuleName("base")								// ModuleName path
                .setMapper("mapper")
                .setEntity("entity")
//           .setEntity("entity")
                .setService("service")
                //.setServiceImpl("service.impl"); 					// auto generate impl， no need to set
                .setController("controller");

        // ==================  custom setting  =================
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        // adjust xml generate directory
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // custom file name
                return projectDir + "/src/main/resources/mybatis/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);

        // ===================  strategy setting  ==================
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel)                    // table name：  underline_to_camel
                .setColumnNaming(NamingStrategy.underline_to_camel)            // file name： underline_to_camel
                .setInclude(tableNames)                                        // tableNames
                .setCapitalMode(true)                                            // enable CapitalMod(ORACLE )
                .setTablePrefix(prefixTable)                                    // remove table prefix
//                 .setFieldPrefix(pc.getModuleName() + "_")					// remove fields prefix
//                 .setSuperEntityClass("com.maoxs.pojo")						// Entity implement
//                 .setSuperControllerClass("com.maoxs.controller")				// Controller implement
//                 .setSuperEntityColumns("id") 								// Super Columns
//                 .setEntityLombokModel(true)									// enable lombok
                .setControllerMappingHyphenStyle(true);                        // controller MappingHyphenStyle

        // ==================  custome template setting：default mybatis-plus/src/main/resources/templates  ======================
        //default: src/main/resources/templates directory
        TemplateConfig tc = new TemplateConfig();
        tc.setXml(null)                                                        // xml template
                .setEntity("/templates/entity.java")                                // entity template
                .setMapper("/templates/mapper.java")                                // mapper template
                .setController("/templates/controller.java")                    // service template
                .setService("/templates/service.java")                            // serviceImpl template
                .setServiceImpl("/templates/serviceImpl.java");                    // controller template

        // ====================  gen setting  ===================
        AutoGenerator mpg = new AutoGenerator();
        mpg.setCfg(cfg)
                .setTemplate(tc)
                .setGlobalConfig(gc)
                .setDataSource(dsc)
                .setPackageInfo(pc)
                .setStrategy(strategy)
                .setTemplateEngine(new FreemarkerTemplateEngine());            // choose freemarker engine，pay attention to pom dependency！
        mpg.execute();
    }

    /**
     * 生成mysql数据库代码
     */
    public void generatorMySQL() {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        final String packNameStr = zeroProperty.getPackageMapperName().replace(".", "/");
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + zeroProperty.getFilePath());
        gc.setAuthor(zeroProperty.getAuthor());
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
        dsc.setUrl(datasourceProperty.getUrl());
        // dsc.setSchemaName("public");
        dsc.setDriverName(datasourceProperty.getDriverClassName());
        dsc.setUsername(datasourceProperty.getUsername());
        dsc.setPassword(datasourceProperty.getPassword());
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
        pc.setParent(zeroProperty.getPackageName());
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("pageDto", zeroProperty.getPackageName());
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
                return projectPath + zeroProperty.getFileMapperPath() + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        focList.add(new FileOutConfig(GeneratorConst.ENTITY_DTO_TEMPLATE_PATH) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + zeroProperty.getFilePath()+ "/" + packNameStr + "/dto" + "/" + tableInfo.getEntityName() + "Dto" + StringPool.DOT_JAVA;
            }
        });
        focList.add(new FileOutConfig(GeneratorConst.ENTITY_ADD_DTO_TEMPLATE_PATH) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + zeroProperty.getFilePath() + "/" + packNameStr + "/dto" + "/" + tableInfo.getEntityName() + "AddDto" + StringPool.DOT_JAVA;
            }
        });
        focList.add(new FileOutConfig(GeneratorConst.ENTITY_MODIFY_DTO_TEMPLATE_PATH) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + zeroProperty.getFilePath() + "/" + packNameStr + "/dto" + "/" + tableInfo.getEntityName() + "ModifyDto" + StringPool.DOT_JAVA;
            }
        });
        focList.add(new FileOutConfig(GeneratorConst.ENTITY_LIST_DTO_TEMPLATE_PATH) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + zeroProperty.getFilePath() + "/" + packNameStr + "/dto" + "/" + tableInfo.getEntityName() + "ListDto" + StringPool.DOT_JAVA;
            }
        });
        focList.add(new FileOutConfig(GeneratorConst.ENTITY_VO_TEMPLATE_PATH) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + zeroProperty.getFilePath() + "/" + packNameStr + "/vo" + "/" + tableInfo.getEntityName() + "Vo" + StringPool.DOT_JAVA;
            }
        });
        focList.add(new FileOutConfig(GeneratorConst.ENTITY_LIST_VO_TEMPLATE_PATH) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + zeroProperty.getFilePath() + "/" + packNameStr + "/vo" + "/" + tableInfo.getEntityName() + "ListVo" + StringPool.DOT_JAVA;
            }
        });
        focList.add(new FileOutConfig(GeneratorConst.ENTITY_DETAIL_TEMPLATE_PATH) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + zeroProperty.getFilePath() + "/" + packNameStr + "/vo" + "/" + tableInfo.getEntityName() + "Detail" + StringPool.DOT_JAVA;
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
        strategy.setSuperEntityClass(zeroProperty.getSuperEntityClass());
        // 自定义 controller 父类 "cn.zero.framework.core.base.controller.WebController"
        strategy.setSuperControllerClass(zeroProperty.getSuperControllerClass());
        // 自定义 mapper 父类 "cn.zero.framework.core.base.mapper.SuperMapper"
        strategy.setSuperMapperClass(zeroProperty.getSuperMapperClass());
        // 自定义 service 父类 "cn.zero.framework.core.base.service.BaseService"
        strategy.setSuperServiceClass(zeroProperty.getSuperServiceClass());
        // 自定义 serviceImpl 父类 "cn.zero.framework.core.base.service.iml.BaseServiceImpl"
        strategy.setSuperServiceImplClass(zeroProperty.getSuperServiceImplClass());

        strategy.setInclude(zeroProperty.getTableName());
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

    // 静态内部类，只有在第一次调用 getInstance 时才会被加载
    private static class SingletonHelper {
        // 持有 GeneratorUtil 的唯一实例
        private static final GeneratorUtil INSTANCE = new GeneratorUtil();
    }

    // 提供全局访问点
    public static GeneratorUtil getInstance() {
        return SingletonHelper.INSTANCE;
    }

}
