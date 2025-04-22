				return new IColorAndFontProvider() {			
					public FontData[] getFont(String symbolicName) {							
						return null;
					}					
					public RGB getColor(String symbolicName) {
						if (expectedSymbolicName.equals(symbolicName)) {
							return expectedRgb;
						}
						return null;
					}
				};
