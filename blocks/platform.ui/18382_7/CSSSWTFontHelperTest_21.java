	
	public void testGetFontByDefinition() throws Exception {
		registerFontProviderWith("org.eclipse.jface.bannerfont", "Arial", 15, SWT.ITALIC);		
		Font previousFont = new Font(display, new FontData("Arial", 15, SWT.ITALIC));
		
		Font currentFont = getFont(new FontByDefinition("org.eclipse.jface.bannerfont", previousFont));
		
		assertEquals(previousFont, currentFont);
		
		previousFont.dispose();
	}
	
	public void testGetFontByDefinitionWhenDefinitionHasBeenChanged() throws Exception {
		registerFontProviderWith("org.eclipse.jface.bannerfont", "Arial", 15, SWT.ITALIC);		
		Font previousFont = new Font(display, new FontData("Times", 15, SWT.NORMAL));
		
		Font currentFont = getFont(new FontByDefinition("org.eclipse.jface.bannerfont", previousFont));
		
		assertNotSame(previousFont, currentFont);
		
		previousFont.dispose();
		currentFont.dispose();
	}
	
	public void testGetFontByDefinitionWhenDefinitionNotFound() throws Exception {
		registerFontProviderWith("org.eclipse.jface.bannerfont", "Arial", 15, SWT.ITALIC);		
		Font previousFont = new Font(display, new FontData("Times", 15, SWT.NORMAL));
		
		Font currentFont = getFont(new FontByDefinition("some not existing definition", previousFont));
		
		assertEquals(previousFont, currentFont);
		
		previousFont.dispose();
	}
