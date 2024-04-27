package com.ifengxue.plugin.state;

import lombok.Data;

@Data
public class ModuleSettings {

    /**
     * file extension
     */
    private String fileExtension = "java";
    private boolean generateEntity = true;
    /**
     * 实体包名
     */
    private String entityPackageName = "";
    /**
     * 实体包parent路径
     * <br> 如: /path/to/maven/module/src/main/java
     */
    private String entityParentDirectory = "";
    private boolean generateRepository = true;
    /**
     * repository包名
     */
    private String repositoryPackageName = "";
    /**
     * repository parent 路径
     * <br> 如:/path/to/maven/module/src/main/java
     */
    private String repositoryParentDirectory = "";
    private boolean generateController;
    /**
     * controller包名
     */
    private String controllerPackageName = "";
    /**
     * controller parent 路径
     * <br> 如:/path/to/maven/module/src/main/java
     */
    private String controllerParentDirectory = "";
    private boolean generateService;
    private boolean repositoryTypeJPA = true;
    private boolean repositoryTypeMybatisPlus = false;
    private boolean repositoryTypeTkMybatis = false;
    /**
     * service包名
     */
    private String servicePackageName = "";
    /**
     * service parent 路径
     * <br> 如:/path/to/maven/module/src/main/java
     */
    private String serviceParentDirectory = "";
    private boolean generateMapperXml;
    /**
     * mapper xml 包名
     */
    private String mapperXmlPackageName = "mapper";
    /**
     * mapper xml parent directory
     */
    private String mapperXmlParentDirectory = "";
    private boolean generateVO;
    /**
     * VO后缀
     */
    private String voSuffixName = "VO";
    /**
     * vo包名
     */
    private String voPackageName = "";
    /**
     * vo parent 路径
     * <br> 如:/path/to/maven/module/src/main/java
     */
    private String voParentDirectory = "";
    private boolean generateDTO;
    /**
     * DTO后缀名称
     */
    private String dtoSuffixName = "DTO";
    /**
     * DTO包名
     */
    private String dtoPackageName = "";
    /**
     * DTO parent 路径
     * <br> 如:/path/to/maven/module/src/main/java
     */
    private String dtoParentDirectory = "";

}
