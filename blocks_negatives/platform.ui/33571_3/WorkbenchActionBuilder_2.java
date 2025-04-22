                if (event.getProperty().equals(
                        ResourcesPlugin.PREF_AUTO_BUILDING)) {
                   	updateBuildActions(false);
                }
            }
        };
        ResourcesPlugin.getPlugin().getPluginPreferences()
                .addPropertyChangeListener(prefListener);

        propPrefListener = new IPropertyChangeListener() {
            @Override
