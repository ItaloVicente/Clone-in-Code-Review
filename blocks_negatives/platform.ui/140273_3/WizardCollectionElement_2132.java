    /**
     * Creates a new <code>WizardCollectionElement</code>. Parent can be
     * null.
     * @param id the id
     * @param pluginId the plugin
     * @param name the name
     * @param parent the parent
     */
    public WizardCollectionElement(String id, String pluginId, String name,
            WizardCollectionElement parent) {
        this.name = name;
        this.id = id;
        this.pluginId = pluginId;
        this.parent = parent;
    }

    /**
     * Creates a new <code>WizardCollectionElement</code>. Parent can be
     * null.
     *
     * @param element
     * @param parent
     * @since 3.1
     */
    public WizardCollectionElement(IConfigurationElement element, WizardCollectionElement parent) {
