    /**
     * Constructs a configuration provider with a given credentials for the REST service
     * @param baseList list of urls to treat as base
     * @param restUsr username
     * @param restPwd password
     * @throws IOException
     */
    public ConfigurationProviderHTTP(List<URI> baseList, String restUsr, String restPwd) throws IOException {
        this.baseList = baseList;
        this.restUsr = restUsr;
        this.restPwd = restPwd;
    }
