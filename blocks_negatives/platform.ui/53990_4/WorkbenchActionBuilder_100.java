        prefListener = new Preferences.IPropertyChangeListener() {
            @Override
			public void propertyChange(Preferences.PropertyChangeEvent event) {
                if (event.getProperty().equals(
                        ResourcesPlugin.PREF_AUTO_BUILDING)) {
                   	updateBuildActions(false);
                }
            }
        };
