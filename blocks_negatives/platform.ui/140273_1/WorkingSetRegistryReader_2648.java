    /**
     * Create a new instance of this reader.
     *
     * @param registry the registry to populate
     */
    public WorkingSetRegistryReader(WorkingSetRegistry registry) {
        super();
        this.registry = registry;
    }

    /**
     * Overrides method in RegistryReader.
     *
     * @see RegistryReader#readElement(IConfigurationElement)
     */
    @Override
