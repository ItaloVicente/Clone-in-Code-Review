        node.putBoolean(ResourcesPlugin.PREF_LIGHTWEIGHT_AUTO_REFRESH, lightweightRefresh);

        try {
            node.flush();
        } catch (BackingStoreException e) {
            IDEWorkbenchPlugin.log(e.getMessage(), e);
        }
