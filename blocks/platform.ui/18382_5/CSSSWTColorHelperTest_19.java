	
	public void testGetSWTColorByDefinition() throws Exception {
		registerColorProviderWith("org.eclipse.ui.workbench.INACTIVE_TAB_TEXT_COLOR", new RGB(255, 0, 0));
		Color previousColor = new Color(display, new RGB(255, 0, 0));
		
		Color currentColor = getSWTColor(new ColorByDefinition("org.eclipse.ui.workbench.INACTIVE_TAB_TEXT_COLOR", previousColor));
		
		assertEquals(previousColor, currentColor);
		
		currentColor.dispose();
	}
	
	public void testGetSWTColorByDefinitionWhenDefinitionHasBeenChanged() throws Exception {
		registerColorProviderWith("org.eclipse.ui.workbench.INACTIVE_TAB_TEXT_COLOR", new RGB(255, 255, 0));
		Color previousColor = new Color(display, new RGB(255, 0, 0));
		
		Color currentColor = getSWTColor(new ColorByDefinition("org.eclipse.ui.workbench.INACTIVE_TAB_TEXT_COLOR", previousColor));
		
		assertNotSame(previousColor, currentColor);
		assertEquals(new RGB(255, 255, 0), currentColor.getRGB());
		
		previousColor.dispose();
		currentColor.dispose();
	}
	
	public void testGetSWTColorByDefinitionWhenDefinitionNotFound() throws Exception {
		registerColorProviderWith("org.eclipse.ui.workbench.INACTIVE_TAB_TEXT_COLOR", new RGB(255, 255, 0));
		Color previousColor = new Color(display, new RGB(255, 0, 0));
		
		Color currentColor = getSWTColor(new ColorByDefinition("not existing color definition", previousColor));
		
		assertEquals(previousColor, currentColor);
		
		currentColor.dispose();
	}
	
	public void testGetSWTColorsByGradient() throws Exception {
		Color color1 = new Color(display, new RGB(255, 0, 0));
		Color color2 = new Color(display, new RGB(255, 255, 0));
		
		CSSPrimitiveValue color1Value = colorValue("rgb(255, 0, 0)");
		CSSPrimitiveValue color2Value = colorValue("rgb(255, 255, 0)");
		
		Gradient gradient = mock(Gradient.class);
		doReturn(Arrays.asList(color1Value, color2Value)).when(gradient).getValues();
		
		CSSEngine engine = mock(CSSEngine.class);
		doReturn(color1).when(engine).convert(color1Value, Color.class, display);
		doReturn(color2).when(engine).convert(color2Value, Color.class, display);
		
		
		Color[] result = getSWTColors(gradient, display, engine);
		
		
		assertEquals(2, result.length);
		assertEquals(color1, result[0]);
		assertEquals(color2, result[1]);
		
		color1.dispose();
		color2.dispose();
	}
