        themeChangeListener = event -> {
		    if (event.getProperty().equals(
		            IThemeManager.CHANGE_CURRENT_THEME)) {
		        updateThemeInfo(themeManager);
		        refreshCategory();
				refreshAllLabels();
		    }
		};
