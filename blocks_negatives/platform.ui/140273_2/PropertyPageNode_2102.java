    /**
     * Create a new instance of the receiver.
     * @param contributor
     * @param element
     */
    public PropertyPageNode(RegistryPageContributor contributor,
            Object element) {
        super(contributor.getPageId(), contributor.getConfigurationElement());
        this.contributor = contributor;
        this.element = element;
    }
