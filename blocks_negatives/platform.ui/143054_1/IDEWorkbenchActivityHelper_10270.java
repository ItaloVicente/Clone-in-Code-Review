    }

    /**
     * For dynamic UI.  Clears the cache of known natures and recreates it.
     */
    public void loadNatures() {
        natureMap.clear();
        IExtensionPoint point = Platform.getExtensionRegistry()
