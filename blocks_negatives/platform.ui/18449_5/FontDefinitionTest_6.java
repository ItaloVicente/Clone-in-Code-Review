				return new IColorAndFontProvider() {
					public FontData[] getFont(String symbolicName) {
						if (expectedSymbolicName.equals(symbolicName)) {
							return new FontData[]{fontData};
						}
						return null;
					}
					public RGB getColor(String symbolicName) {
						return null;
					}
				};
