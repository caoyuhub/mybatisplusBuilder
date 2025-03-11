package www.cy27.cn.mybatispluscodegeneration.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import www.cy27.cn.mybatispluscodegeneration.entity.DataSource;
import www.cy27.cn.mybatispluscodegeneration.entity.GenerationParameters;
import www.cy27.cn.mybatispluscodegeneration.entity.IdTypeSelect;
import www.cy27.cn.mybatispluscodegeneration.form.MainForm;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ParametersUtil {

    public static GenerationParameters getFileGenerationParameters(MainForm mainForm){
        File mybatisPlusSettingsFile = new File(mainForm.e.getProject().getBasePath() + "/.idea","mybatisPlusSettings");
        GenerationParameters generationParameters;
        if(!mybatisPlusSettingsFile.exists()){
            generationParameters = new GenerationParameters();
            FileUtil.writeString(new JSONObject(generationParameters).toJSONString(1),mybatisPlusSettingsFile, StandardCharsets.UTF_8);
        }
        generationParameters = new JSONObject(FileUtil.readString(mybatisPlusSettingsFile,StandardCharsets.UTF_8)).toBean(GenerationParameters.class);
        return generationParameters;
    }

    public static void writeFileGenerationParameters(MainForm mainForm,GenerationParameters generationParameters){
        File mybatisPlusSettingsFile = new File(mainForm.e.getProject().getBasePath() + "/.idea","mybatisPlusSettings");
        FileUtil.writeString(new JSONObject(generationParameters).toJSONString(1),mybatisPlusSettingsFile,StandardCharsets.UTF_8);
    }

    public static GenerationParameters getParameters(MainForm mainForm)  {
        GenerationParameters generationParameters = getFileGenerationParameters(mainForm);

        DataSource dataSource = mainForm.datasourceSelect.getItem();
        if(dataSource != null){
            generationParameters.setDataSourceId(dataSource.getId());
        }
        generationParameters.setOutputDir(mainForm.outputDir.getText());
        generationParameters.setOpen(mainForm.open.isSelected());
        generationParameters.setAuthor(mainForm.author.getText());
        generationParameters.setKotlin(mainForm.kotlin.isSelected());
        generationParameters.setSwagger(mainForm.swagger.isSelected());
        generationParameters.setSpringdoc(mainForm.springDoc.isSelected());
        generationParameters.setRestController(mainForm.restController.isSelected());
        generationParameters.setResultMap(mainForm.resultMap.isSelected());
        generationParameters.setLombok(mainForm.lombok.isSelected());

        generationParameters.setParent(mainForm.packagePath.getText());
        generationParameters.setEntity(mainForm.entityPackage.getText());
        generationParameters.setService(mainForm.servicePackage.getText());
        generationParameters.setServiceImpl(mainForm.serviceImplPackage.getText());
        generationParameters.setMapper(mainForm.mapperPackage.getText());
        generationParameters.setController(mainForm.controllerPackage.getText());

        generationParameters.setGenerateEntity(mainForm.entity.isSelected());
        generationParameters.setGenerateService(mainForm.service.isSelected());
        generationParameters.setGenerateServiceImpl(mainForm.serviceImpl.isSelected());
        generationParameters.setGenerateMapper(mainForm.mapper.isSelected());
        generationParameters.setGenerateController(mainForm.controller.isSelected());

        generationParameters.setParentEntity(mainForm.entityParent.getText());
        generationParameters.setIgnoreField(mainForm.entityField.getText());
        generationParameters.setParentController(mainForm.controllerParent.getText());

        generationParameters.setIdType(mainForm.idType.getItem().getCode());
        generationParameters.setEntitySuffix(mainForm.entitySuffix.getText());

        writeFileGenerationParameters(mainForm,generationParameters);
        return generationParameters;
    }


    public static void fillFrom(MainForm mainForm){
        GenerationParameters generationParameters = getFileGenerationParameters(mainForm);

        mainForm.outputDir.setText(generationParameters.getOutputDir());
        mainForm.open.setSelected(generationParameters.isOpen());
        mainForm.author.setText(generationParameters.getAuthor());
        mainForm.kotlin.setSelected(generationParameters.isKotlin());
        mainForm.swagger.setSelected(generationParameters.isSwagger());
        mainForm.lombok.setSelected(generationParameters.isLombok());

        mainForm.springDoc.setSelected(generationParameters.isSpringdoc());
        mainForm.restController.setSelected(generationParameters.isRestController());
        mainForm.resultMap.setSelected(generationParameters.isResultMap());

        mainForm.packagePath.setText(generationParameters.getParent());
        mainForm.entityPackage.setText(generationParameters.getEntity());
        mainForm.servicePackage.setText(generationParameters.getService());
        mainForm.serviceImplPackage.setText(generationParameters.getServiceImpl());
        mainForm.mapperPackage.setText(generationParameters.getMapper());
        mainForm.controllerPackage.setText(generationParameters.getController());

        mainForm.entity.setSelected(generationParameters.isGenerateEntity());
        mainForm.service.setSelected(generationParameters.isGenerateService());
        mainForm.serviceImpl.setSelected(generationParameters.isGenerateServiceImpl());
        mainForm.mapper.setSelected(generationParameters.isGenerateMapper());
        mainForm.controller.setSelected(generationParameters.isGenerateController());

        mainForm.entityParent.setText(generationParameters.getParentEntity());
        mainForm.entityField.setText(generationParameters.getIgnoreField());
        mainForm.controllerParent.setText(generationParameters.getParentController());

        mainForm.idType.setSelectedItem(IdTypeSelect.getByCode(generationParameters.getIdType()));
        mainForm.entitySuffix.setText(generationParameters.getEntitySuffix());


        if(ObjectUtil.isNotEmpty(generationParameters.getDataSourceId())){
            DataSource dataSource = DatasourceUtil.getById(generationParameters.getDataSourceId());
            mainForm.datasourceSelect.setSelectedItem(dataSource);
        }


    }
}
