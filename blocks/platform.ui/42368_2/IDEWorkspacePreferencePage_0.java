        node.putBoolean(ResourcesPlugin.PREF_LIGHTWEIGHT_AUTO_REFRESH, lightweightRefresh);
        
        try {
            node.flush();
        } catch (BackingStoreException e) {
            IDEWorkbenchPlugin.log(
                    "Error saving autoRefresh and lightweightRefresh preferences.", e);//$NON-NLS-1$
        }
