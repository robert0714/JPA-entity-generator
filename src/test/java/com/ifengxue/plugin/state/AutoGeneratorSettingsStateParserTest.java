package com.ifengxue.plugin.state;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AutoGeneratorSettingsStateParserTest {

	@BeforeEach
	protected void setUp() throws Exception {
	}

	@AfterEach
	protected void tearDown() throws Exception {
	}

	@Test
	public void testParser() {
		AutoGeneratorSettingsState state = AutoGeneratorSettingsStateParser.parser();
		assertNotNull(state);
		assertNotNull(state.getModuleName());
		assertNotNull(state.getModuleSettings());
		assertNotNull(state.getModuleSettings(state.getModuleName()));
		assertNotNull(state.getRemoveEntityPrefix());
		assertNotNull(state.getRemoveFieldPrefix());
		assertNotNull(state.isUseJakartaEE());
		
		ModuleSettings moduleSettings = state.getModuleSettings(state.getModuleName() );
		
		assertEquals("$PROJECT_DIR$/src/main/java", moduleSettings.getControllerParentDirectory());
		assertEquals("com.company.project.ocapi.generate.dto", moduleSettings.getDtoPackageName());
		assertEquals("$PROJECT_DIR$/src/main/java", moduleSettings.getDtoParentDirectory());
		assertEquals("com.company.project.ocapi.entity", moduleSettings.getEntityPackageName());
		assertEquals("$PROJECT_DIR$/src/main/java" , moduleSettings.getEntityParentDirectory() );
		assertTrue(moduleSettings.isGenerateDTO());
//		assertFalse(moduleSettings.isGenerateRepository());
		assertEquals(moduleSettings.getMapperXmlParentDirectory() ,"D:\\projects\\oc064\\src\\main\\resources" );
		assertEquals(moduleSettings.getRepositoryPackageName() ,"com.company.project.ocapi.repository"  );
		
		assertEquals(moduleSettings.getRepositoryParentDirectory() , "$PROJECT_DIR$/src/main/java");
		assertEquals(moduleSettings.getServicePackageName() , "com.company.project.ocapi.component.service");
		assertEquals(moduleSettings.getServiceParentDirectory() ,"$PROJECT_DIR$/src/main/java" );
		assertEquals(moduleSettings.getVoParentDirectory() , "$PROJECT_DIR$/src/main/java");
	}

}
