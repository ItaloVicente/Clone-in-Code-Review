	public void testHasColorDefinitionAsValue() throws Exception {
		boolean result = hasColorDefinitionAsValue(colorValue(addColorDefinitionMarker("org-eclipse-ui-workbench-INACTIVE_TAB_TEXT_COLOR")));
		
		assertTrue(result);		
	}
	
	public void testHasColorDefinitionAsValueWhenHexColorValue() throws Exception {
		boolean result = hasColorDefinitionAsValue(colorValue("#FF00ab"));
		
		assertFalse(result);
	}
	
	public void testHasColorDefinitionAsValueWhenOtherColorValue() throws Exception {
		boolean result = hasColorDefinitionAsValue(colorValue("red"));
		
		assertFalse(result);
	}
	
