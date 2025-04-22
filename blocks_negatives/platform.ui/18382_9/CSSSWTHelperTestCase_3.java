	
	protected void registerColorProviderWith(final String expectedSymbolicName, final RGB rgb) throws Exception {
		registerProvider(new IColorAndFontProvider() {			
			public FontData[] getFont(String symbolicName) {		
				return null;
			}					
			public RGB getColor(String symbolicName) {		
				if (symbolicName.equals(expectedSymbolicName)) {
					return rgb;
				}
				return null;
			}
		});
