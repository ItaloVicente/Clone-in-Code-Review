    /**
     * Reads the registry and registers popup menu contributors
     * found there.
     *
     * @param mng the manager to read into
     */
    public void readPopupContributors(ObjectActionContributorManager mng) {
        setManager(mng);
        IExtensionRegistry registry = Platform.getExtensionRegistry();
        readRegistry(registry, PlatformUI.PLUGIN_ID,
                IWorkbenchRegistryConstants.PL_POPUP_MENU);
    }
