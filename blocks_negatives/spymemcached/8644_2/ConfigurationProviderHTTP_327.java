    /**
     * For a given bucket to be found, walk the URIs in the baselist until the
     * bucket needed is found.
     *
     * @param bucketToFind
     * @throws ConfigurationException
     */
    private void readPools(String bucketToFind) throws ConfigurationException {
        for (URI baseUri : baseList) {
            try {
                URLConnection baseConnection = urlConnBuilder(null, baseUri);
                String base = readToString(baseConnection);
                if ("".equals(base)) {
                    getLogger().warn("Provided URI " + baseUri + " has an empty response... skipping");
                    continue;
                }
                Map<String, Pool> pools = this.configurationParser.parseBase(base);
