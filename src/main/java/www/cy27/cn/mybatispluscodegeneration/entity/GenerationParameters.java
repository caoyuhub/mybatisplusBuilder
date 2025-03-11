package www.cy27.cn.mybatispluscodegeneration.entity;

import com.baomidou.mybatisplus.annotation.IdType;

public class GenerationParameters {

    /**
     * 数据源ID，用于回显上次选中的数据源
     */
    String dataSourceId;

    /**
     * 输出路径
     */
    String outputDir;
    /**
     * 是否打开文件
     */
    boolean open = false;
    /**
     * 作者
     */
    String author = "caoyu";
    /**
     * 开启 Kotlin 模式（默认 false）
     */
    boolean kotlin = false;

    /**
     * 使用 lombok
     */
    boolean lombok = true;

    /**
     * 开启 swagger 模式（默认 false 与 springdoc 不可同时使用）
     */
    boolean swagger = true;
    /**
     * 开启 springdoc 模式（默认 false 与 swagger 不可同时使用）
     */
    boolean springdoc = false;
    /**
     * 是否生成restController
     */
    boolean restController = true;
    /**
     * 是否生成resultMap
     */
    boolean resultMap = false;


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 父包名。如果为空，将下面子包名必须写全部， 否则就只需写子包名
     */
    private String parent = "www.cy27.cn";

    /**
     * Entity包名
     */
    private String entity = "entity";

    /**
     * Service包名
     */
    private String service = "service";

    /**
     * Service Impl包名
     */
    private String serviceImpl = "service.impl";

    /**
     * Mapper包名
     */
    private String mapper = "mapper";

    /**
     * Mapper XML包名
     */
    private String xml;

    /**
     * Controller包名
     */
    private String controller = "controller";

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 是否生成实体类
     */
    boolean generateEntity = true;
    /**
     * 是否生成service
     */
    boolean generateService = true;
    /**
     * 是否生成service impl
     */
    boolean generateServiceImpl = true;
    /**
     * 是否生成mapper
     */
    boolean generateMapper = true;
    /**
     * 是否生成controller
     */
    boolean generateController = true;

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 实体类父类
     */
    String parentEntity;
    /**
     * 忽略的字段
     */
    String ignoreField;
    /**
     * controller父类
     */
    String parentController;

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    String idType = IdType.ASSIGN_ID.name();

    String entitySuffix = "Entity";

    public String getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public String getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isKotlin() {
        return kotlin;
    }

    public void setKotlin(boolean kotlin) {
        this.kotlin = kotlin;
    }

    public boolean isSwagger() {
        return swagger;
    }

    public void setSwagger(boolean swagger) {
        this.swagger = swagger;
    }

    public boolean isSpringdoc() {
        return springdoc;
    }

    public void setSpringdoc(boolean springdoc) {
        this.springdoc = springdoc;
    }

    public boolean isRestController() {
        return restController;
    }

    public void setRestController(boolean restController) {
        this.restController = restController;
    }

    public boolean isResultMap() {
        return resultMap;
    }

    public void setResultMap(boolean resultMap) {
        this.resultMap = resultMap;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getServiceImpl() {
        return serviceImpl;
    }

    public void setServiceImpl(String serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    public String getMapper() {
        return mapper;
    }

    public void setMapper(String mapper) {
        this.mapper = mapper;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public boolean isGenerateEntity() {
        return generateEntity;
    }

    public void setGenerateEntity(boolean generateEntity) {
        this.generateEntity = generateEntity;
    }

    public boolean isGenerateService() {
        return generateService;
    }

    public void setGenerateService(boolean generateService) {
        this.generateService = generateService;
    }

    public boolean isGenerateServiceImpl() {
        return generateServiceImpl;
    }

    public void setGenerateServiceImpl(boolean generateServiceImpl) {
        this.generateServiceImpl = generateServiceImpl;
    }

    public boolean isGenerateMapper() {
        return generateMapper;
    }

    public void setGenerateMapper(boolean generateMapper) {
        this.generateMapper = generateMapper;
    }

    public boolean isGenerateController() {
        return generateController;
    }

    public void setGenerateController(boolean generateController) {
        this.generateController = generateController;
    }

    public String getParentEntity() {
        return parentEntity;
    }

    public void setParentEntity(String parentEntity) {
        this.parentEntity = parentEntity;
    }

    public String getIgnoreField() {
        return ignoreField;
    }

    public void setIgnoreField(String ignoreField) {
        this.ignoreField = ignoreField;
    }

    public String getParentController() {
        return parentController;
    }

    public void setParentController(String parentController) {
        this.parentController = parentController;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public boolean isLombok() {
        return lombok;
    }

    public void setLombok(boolean lombok) {
        this.lombok = lombok;
    }

    public String getEntitySuffix() {
        return entitySuffix;
    }

    public void setEntitySuffix(String entitySuffix) {
        this.entitySuffix = entitySuffix;
    }
}
