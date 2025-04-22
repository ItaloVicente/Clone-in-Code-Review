        setPreferenceStore(PrefUtil.getInternalPreferenceStore());

        final IThemeManager themeManager = aWorkbench.getThemeManager();
        themeChangeListener = event -> {
		    if (event.getProperty().equals(
		            IThemeManager.CHANGE_CURRENT_THEME)) {
		        updateThemeInfo(themeManager);
		        refreshCategory();
