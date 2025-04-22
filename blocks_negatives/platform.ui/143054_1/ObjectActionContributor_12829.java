    /**
     * The constructor.
     *
     * @param config the element
     */
    public ObjectActionContributor(IConfigurationElement config) {
        this.config = config;
        this.adaptable = P_TRUE.equalsIgnoreCase(config
                .getAttribute(IWorkbenchRegistryConstants.ATT_ADAPTABLE));
        this.objectClass = config.getAttribute(IWorkbenchRegistryConstants.ATT_OBJECTCLASS);
    }
