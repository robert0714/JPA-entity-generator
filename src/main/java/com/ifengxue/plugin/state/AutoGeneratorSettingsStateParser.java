package com.ifengxue.plugin.state;

import java.io.InputStream;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory; 
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AutoGeneratorSettingsStateParser {
	public static final String EXPRESSION_MODULE_NAME =  
			"/project//component[@name='AutoGeneratorSettingsState']//option[@name='moduleName']//@value";
	public static final String EXPRESSION_CTL_PARENT_DIR =  
			"/project//component[@name='AutoGeneratorSettingsState']//option[@name='moduleNameToSettings']//map//entry//value//ModuleSettings//option[@name='controllerParentDirectory']//@value";
	public static final String EXPRESSION_DTO_PACKAGE_NAME =  
			"/project//component[@name='AutoGeneratorSettingsState']//option[@name='moduleNameToSettings']//map//entry//value//ModuleSettings//option[@name='dtoPackageName']//@value";
	public static final String EXPRESSION_DTO_PARENT_DIRECTORY =  
			"/project//component[@name='AutoGeneratorSettingsState']//option[@name='moduleNameToSettings']//map//entry//value//ModuleSettings//option[@name='dtoParentDirectory']//@value";
	
	public static final String EXPRESSION_ENTITY_PACKAGE_NAME =  
			"/project//component[@name='AutoGeneratorSettingsState']//option[@name='moduleNameToSettings']//map//entry//value//ModuleSettings//option[@name='entityPackageName']//@value";
	
	public static final String EXPRESSION_ENTITY_PARENT_DIRECTORY =  
			"/project//component[@name='AutoGeneratorSettingsState']//option[@name='moduleNameToSettings']//map//entry//value//ModuleSettings//option[@name='entityParentDirectory']//@value";

	public static final String EXPRESSION_GENERATE_DTO =  
			"/project//component[@name='AutoGeneratorSettingsState']//option[@name='moduleNameToSettings']//map//entry//value//ModuleSettings//option[@name='generateDTO']//@value";
	
	public static final String EXPRESSION_GENERATE_REPOSITORY =  
			"/project//component[@name='AutoGeneratorSettingsState']//option[@name='moduleNameToSettings']//map//entry//value//ModuleSettings//option[@name='generateRepository']//@value";
	public static final String EXPRESSION_MAPPER_XML_PARENT_DIRECTORY =  
			"/project//component[@name='AutoGeneratorSettingsState']//option[@name='moduleNameToSettings']//map//entry//value//ModuleSettings//option[@name='mapperXmlParentDirectory']//@value";
	
	public static final String EXPRESSION_REPOSITORY_PACKAGE_NAME =  
			"/project//component[@name='AutoGeneratorSettingsState']//option[@name='moduleNameToSettings']//map//entry//value//ModuleSettings//option[@name='repositoryPackageName']//@value";
	
	public static final String EXPRESSION_REPOSITORY_PARENT_DIRECTORY =  
			"/project//component[@name='AutoGeneratorSettingsState']//option[@name='moduleNameToSettings']//map//entry//value//ModuleSettings//option[@name='repositoryParentDirectory']//@value";
	
	public static final String EXPRESSION_SERVICE_PACKAGE_NAME =  
			"/project//component[@name='AutoGeneratorSettingsState']//option[@name='moduleNameToSettings']//map//entry//value//ModuleSettings//option[@name='servicePackageName']//@value";
	
	public static final String EXPRESSION_SERVICE_PARENT_DIRECTORY =  
			"/project//component[@name='AutoGeneratorSettingsState']//option[@name='moduleNameToSettings']//map//entry//value//ModuleSettings//option[@name='serviceParentDirectory']//@value";
	
	public static final String EXPRESSION_VO_PARENT_DIRECTORY =  
			"/project//component[@name='AutoGeneratorSettingsState']//option[@name='moduleNameToSettings']//map//entry//value//ModuleSettings//option[@name='voParentDirectory']//@value";
	
	public static AutoGeneratorSettingsState parser() {
		ApplicationContext ctx = new AnnotationConfigApplicationContext();
		AutoGeneratorSettingsState autoGeneratorSettingsState = new AutoGeneratorSettingsState();
		try (InputStream inputStream = ctx.getResource("classpath:JPASupport-project.xml").getInputStream();) {

			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();

			Document xmlDocument = builder.parse(inputStream);
			XPath xPath = XPathFactory.newInstance().newXPath();
			
			//https://codebeautify.org/Xpath-Tester
			String moduleName = (String)xPath.compile(EXPRESSION_MODULE_NAME).evaluate(xmlDocument, XPathConstants.STRING);
			
			String controllerParentDirectory = (String)xPath.compile(EXPRESSION_CTL_PARENT_DIR).evaluate(xmlDocument, XPathConstants.STRING);
			String dtoPackageName = (String)xPath.compile(EXPRESSION_DTO_PACKAGE_NAME).evaluate(xmlDocument, XPathConstants.STRING);
			String dtoParentDirectory = (String)xPath.compile(EXPRESSION_DTO_PARENT_DIRECTORY).evaluate(xmlDocument, XPathConstants.STRING);
			String entityPackageName = (String)xPath.compile(EXPRESSION_ENTITY_PACKAGE_NAME).evaluate(xmlDocument, XPathConstants.STRING);
			String entityParentDirectory =  (String)xPath.compile(EXPRESSION_ENTITY_PARENT_DIRECTORY).evaluate(xmlDocument, XPathConstants.STRING);
			boolean generateDTO =  (Boolean)xPath.compile(EXPRESSION_GENERATE_DTO).evaluate(xmlDocument, XPathConstants.BOOLEAN);
			boolean generateRepository =  (Boolean)xPath.compile(EXPRESSION_GENERATE_REPOSITORY).evaluate(xmlDocument, XPathConstants.BOOLEAN);
			String mapperXmlParentDirectory =  (String)xPath.compile(EXPRESSION_MAPPER_XML_PARENT_DIRECTORY).evaluate(xmlDocument, XPathConstants.STRING);
			String repositoryPackageName = (String)xPath.compile(EXPRESSION_REPOSITORY_PACKAGE_NAME).evaluate(xmlDocument, XPathConstants.STRING);
			String repositoryParentDirectory = (String)xPath.compile(EXPRESSION_REPOSITORY_PARENT_DIRECTORY).evaluate(xmlDocument, XPathConstants.STRING);
			String servicePackageName = (String)xPath.compile(EXPRESSION_SERVICE_PACKAGE_NAME).evaluate(xmlDocument, XPathConstants.STRING);
			String serviceParentDirectory = (String)xPath.compile(EXPRESSION_SERVICE_PARENT_DIRECTORY).evaluate(xmlDocument, XPathConstants.STRING);
			String voParentDirectory = (String)xPath.compile(EXPRESSION_VO_PARENT_DIRECTORY).evaluate(xmlDocument, XPathConstants.STRING);
			
			autoGeneratorSettingsState.setModuleName(moduleName);
			ModuleSettings moduleSettings = autoGeneratorSettingsState.getModuleSettings(moduleName);
			
			
			moduleSettings.setControllerParentDirectory(controllerParentDirectory);
			moduleSettings.setDtoPackageName(dtoPackageName);
			moduleSettings.setDtoParentDirectory(dtoParentDirectory);			
			moduleSettings.setEntityPackageName(entityPackageName);
			moduleSettings.setEntityParentDirectory(entityParentDirectory);
			moduleSettings.setGenerateDTO(generateDTO);	
			
			moduleSettings.setGenerateRepository(generateRepository);	
			moduleSettings.setMapperXmlParentDirectory(mapperXmlParentDirectory);
			moduleSettings.setRepositoryPackageName(repositoryPackageName);	
			moduleSettings.setRepositoryParentDirectory(repositoryParentDirectory);	
			moduleSettings.setServicePackageName(servicePackageName);	
			moduleSettings.setServiceParentDirectory(serviceParentDirectory);
			moduleSettings.setVoParentDirectory(voParentDirectory);
			 //			autoGeneratorSettingsState.setModuleNameToSettings(Map.of(moduleName ,moduleSettings ));
			
		} catch (Exception exc) {
			log.error(exc.getMessage(), exc);

		}

		return autoGeneratorSettingsState;
	}
}
