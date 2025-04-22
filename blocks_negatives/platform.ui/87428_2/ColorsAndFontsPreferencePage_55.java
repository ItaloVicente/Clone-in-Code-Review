        themeChangeListener = new IPropertyChangeListener() {
            @Override
			public void propertyChange(PropertyChangeEvent event) {
                if (event.getProperty().equals(
                        IThemeManager.CHANGE_CURRENT_THEME)) {
                    updateThemeInfo(themeManager);
                    refreshCategory();
					refreshAllLabels();
                }
            }
        };
