    	isDisposed = true;

    	getActionBarConfigurer().getStatusLineManager().remove(statusLineItem);
        if (pageListener != null) {
            window.removePageListener(pageListener);
            pageListener = null;
        }
        if (prefListener != null) {
            ResourcesPlugin.getPlugin().getPluginPreferences()
                    .removePropertyChangeListener(prefListener);
            prefListener = null;
        }
        if (propPrefListener != null) {
            WorkbenchPlugin.getDefault().getPreferenceStore()
                    .removePropertyChangeListener(propPrefListener);
            propPrefListener = null;
        }
        if (resourceListener != null) {
            ResourcesPlugin.getWorkspace().removeResourceChangeListener(resourceListener);
            resourceListener = null;
        }

        showInQuickMenu.dispose();
        newQuickMenu.dispose();
        

        closeAction = null;
        closeAllAction = null;
        closeAllSavedAction = null;
        closeOthersAction = null;
        saveAction = null;
        saveAllAction = null;
        newWindowAction = null;
        helpContentsAction = null;
        helpSearchAction = null;
