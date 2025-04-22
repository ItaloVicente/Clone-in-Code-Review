    /**
     * Constructs a configuration provider with disabled authentication for the REST service
     * @param baseList list of urls to treat as base
     * @throws IOException
     */
    public ConfigurationProviderHTTP(List<URI> baseList) throws IOException {
        this(baseList, null, null);
    }
