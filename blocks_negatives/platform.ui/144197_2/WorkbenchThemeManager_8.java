			{
				ColorRegistry jfaceColors = JFaceResources.getColorRegistry();
				ColorRegistry themeColors = currentTheme.getColorRegistry();
				for (Object themeColorKey : themeColors.getKeySet()) {
					String key = (String) themeColorKey;
					jfaceColors.put(key, themeColors.getRGB(key));
				}
