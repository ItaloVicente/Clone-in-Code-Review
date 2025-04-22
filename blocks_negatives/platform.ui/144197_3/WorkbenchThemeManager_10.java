			{
				FontRegistry jfaceFonts = JFaceResources.getFontRegistry();
				FontRegistry themeFonts = currentTheme.getFontRegistry();
				for (Object themeFontKey : themeFonts.getKeySet()) {
					String key = (String) themeFontKey;
					jfaceFonts.put(key, themeFonts.getFontData(key));
				}
