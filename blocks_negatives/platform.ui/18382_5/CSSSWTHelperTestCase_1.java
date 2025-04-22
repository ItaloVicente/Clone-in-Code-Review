	protected void registerFontProviderWith(final String expectedSymbolicName, final String family, final int size, final int style) throws Exception {
		registerProvider(new IColorAndFontProvider() {			
			public FontData[] getFont(String symbolicName) {	
				if (symbolicName.equals(expectedSymbolicName)) {
					return new FontData[]{new FontData(family, size, style)};
				}
				return null;
			}					
			public RGB getColor(String symbolicName) {
				return null;
			}
		});
