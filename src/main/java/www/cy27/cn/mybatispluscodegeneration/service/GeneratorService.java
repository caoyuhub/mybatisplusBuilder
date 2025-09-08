package www.cy27.cn.mybatispluscodegeneration.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import www.cy27.cn.mybatispluscodegeneration.entity.DataSource;
import www.cy27.cn.mybatispluscodegeneration.entity.GenerationParameters;
import www.cy27.cn.mybatispluscodegeneration.entity.IdTypeSelect;
import www.cy27.cn.mybatispluscodegeneration.form.MainForm;
import www.cy27.cn.mybatispluscodegeneration.generator.MyDefaultQuery;
import www.cy27.cn.mybatispluscodegeneration.util.ParametersUtil;

import javax.swing.table.TableModel;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GeneratorService {
    DataSource dataSource;

    MainForm mainForm;




    public GeneratorService(DataSource dataSource, MainForm mainForm){
        this.dataSource = dataSource;
        this.mainForm = mainForm;
    }


    /**
     * 获取所有表的信息
     * @return
     */
    public List<TableInfo> getTableInfoList(){
        //数据源
        DataSourceConfig.Builder dataSourceConfigBuilder =  new DataSourceConfig.Builder(
                dataSource.getUrl(),
                dataSource.getUsername(),
                dataSource.getPassword());

        dataSourceConfigBuilder.schema(dataSource.getSchema());
        dataSourceConfigBuilder.databaseQueryClass(MyDefaultQuery.class);

        StrategyConfig.Builder strategyConfig = new StrategyConfig.Builder();
        //跳过视图
//        strategyConfig.enableSkipView();
        //添加表名，只要字母、数字、下划线、空格，其他名字的表可能会导致出错
        // 整串校验（必须从头到尾都是合法字符）
        String regex = "^[A-Za-z0-9_ ]+$";
        strategyConfig.addInclude(
                regex
        );

        ConfigBuilder config = new ConfigBuilder(
                null,
                dataSourceConfigBuilder.build(),
                strategyConfig.build(),
                null,
                null,
                null);
        return config.getTableInfoList();
    }


    /**
     * 开始执行生成操作
     */
    public void execute()  {

        int[] selectedRows = mainForm.datasourceTable.getSelectedRows();
        if(selectedRows.length == 0){
            throw new RuntimeException("未选择生成的表信息");
        }
        TableModel model = mainForm.datasourceTable.getModel();
        List<String > tableNameList = new ArrayList<>();
        for(int rows:selectedRows){
            String tableName = model.getValueAt(rows, 0).toString();
            tableNameList.add(tableName);
        }



        DataSourceConfig.Builder dataSourceConfig = new DataSourceConfig.Builder(
                dataSource.getUrl(),
                dataSource.getUsername(),
                dataSource.getPassword()
        );

        dataSourceConfig.schema(dataSource.getSchema());
        dataSourceConfig.databaseQueryClass(MyDefaultQuery.class);

        GenerationParameters generationParameters = ParametersUtil.getParameters(mainForm);

        /////////////////////////////////////////////////////////////////////////////////
        GlobalConfig.Builder globalConfig = new GlobalConfig.Builder();
        globalConfig.outputDir(generationParameters.getOutputDir()+"/src/main/java");
        if(!generationParameters.isOpen()){
            globalConfig.disableOpenDir();
        }
        globalConfig.author(generationParameters.getAuthor());
        if(generationParameters.isKotlin()){
            globalConfig.enableKotlin() ;
        }

        if(generationParameters.isSwagger()){
            globalConfig.enableSwagger();
        }
        if(generationParameters.isSpringdoc()){
            globalConfig.enableSpringdoc();
        }

        /////////////////////////////////////////////////////////////////////////////////
        PackageConfig.Builder packageConfig = new PackageConfig.Builder();
        packageConfig.parent(generationParameters.getParent());
        packageConfig.service(generationParameters.getService());
        packageConfig.entity(generationParameters.getEntity());
        packageConfig.serviceImpl(generationParameters.getServiceImpl());
        packageConfig.mapper(generationParameters.getMapper());
//        packageConfig.xml("/src/main/resources/mapper");
        packageConfig.controller(generationParameters.getController());
        packageConfig.pathInfo(Collections.singletonMap(OutputFile.xml, generationParameters.getOutputDir() + "/src/main/resources/mapper"));

        /////////////////////////////////////////////////////////////////////////////////


        StrategyConfig.Builder strategyConfig = new StrategyConfig.Builder();
        if(!generationParameters.isGenerateEntity()){
            strategyConfig.entityBuilder().disable();
        }

        if(!generationParameters.isGenerateService()){
            strategyConfig.serviceBuilder().disableService();
        }

        if(!generationParameters.isGenerateServiceImpl()){
            strategyConfig.serviceBuilder().disableServiceImpl();
        }

        if(!generationParameters.isGenerateMapper()){
            strategyConfig.mapperBuilder().disable();
        }

        if(!generationParameters.isGenerateController()){
            strategyConfig.controllerBuilder().disable();
        }

        if(ObjectUtil.isNotEmpty(generationParameters.getParentEntity())){
            strategyConfig.entityBuilder().superClass(generationParameters.getParentEntity());
        }

        if(ObjectUtil.isNotEmpty(generationParameters.getIgnoreField())){
            strategyConfig.entityBuilder().addSuperEntityColumns(generationParameters.getIgnoreField().split(","));
        }

        if(ObjectUtil.isNotEmpty(generationParameters.getParentController())){
            strategyConfig.controllerBuilder().superClass(generationParameters.getParentController());
        }

        if(ObjectUtil.isNotEmpty(generationParameters.isLombok())){
            strategyConfig.entityBuilder().enableLombok();
        }

        strategyConfig.entityBuilder().idType(IdType.valueOf(generationParameters.getIdType()));

        if(generationParameters.isRestController()){
            strategyConfig.controllerBuilder().enableRestStyle();
        }

        if(generationParameters.isResultMap()){
            strategyConfig.mapperBuilder().enableBaseResultMap();
        }

        strategyConfig.addInclude(tableNameList);

        if(ObjectUtil.isNotEmpty(generationParameters.getEntitySuffix())){
            // 实体类后缀
            strategyConfig.entityBuilder().nameConvert(new INameConvert() {
                @Override
                public @NotNull String entityNameConvert(@NotNull TableInfo tableInfo) {
                    return new INameConvert.DefaultNameConvert(strategyConfig.build()).entityNameConvert(tableInfo) + generationParameters.getEntitySuffix();
                }

                @Override
                public @NotNull String propertyNameConvert(@NotNull TableField field) {
                    return new INameConvert.DefaultNameConvert(strategyConfig.build()).propertyNameConvert(field);
                }
            });
        }

        //Service 文件名称
        strategyConfig.serviceBuilder().convertServiceFileName(entityName -> entityName.substring(0,entityName.length()-generationParameters.getEntitySuffix().length())+"Service");
        //Service Impl  文件名称
        strategyConfig.serviceBuilder().convertServiceImplFileName(entityName -> entityName.substring(0,entityName.length()-generationParameters.getEntitySuffix().length())+"ServiceImpl");
        //Mapper JAVA 文件名称
        strategyConfig.mapperBuilder().convertMapperFileName(entityName -> entityName.substring(0,entityName.length()-generationParameters.getEntitySuffix().length())+"Mapper");
        //Mapper XML 文件名称
        strategyConfig.mapperBuilder().convertXmlFileName(entityName -> entityName.substring(0,entityName.length()-generationParameters.getEntitySuffix().length())+"Mapper");
        // Controller 文件名称
        strategyConfig.controllerBuilder().convertFileName(entityName -> entityName.substring(0,entityName.length()-generationParameters.getEntitySuffix().length())+"Controller");

        AutoGenerator autoGenerator = new AutoGenerator(dataSourceConfig.build());
        autoGenerator.global(globalConfig.build());
        autoGenerator.packageInfo(packageConfig.build());
        autoGenerator.strategy(strategyConfig.build());
        autoGenerator.execute(new FreemarkerTemplateEngine());
    }





}
