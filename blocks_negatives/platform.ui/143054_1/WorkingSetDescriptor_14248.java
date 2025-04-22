    /**
     * Creates a descriptor from a configuration element.
     *
     * @param configElement configuration element to create a descriptor from
     */
    public WorkingSetDescriptor(IConfigurationElement configElement)
            throws CoreException {
        super();
        this.configElement = configElement;
        id = configElement.getAttribute(ATT_ID);
        name = configElement.getAttribute(ATT_NAME);
        icon = configElement.getAttribute(ATT_ICON);
        pageClassName = configElement.getAttribute(ATT_PAGE_CLASS);
        updaterClassName = configElement.getAttribute(ATT_UPDATER_CLASS);
