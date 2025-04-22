
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

                if (!pools.containsKey(DEFAULT_POOL_NAME)) {
                    getLogger().warn("Provided URI " + baseUri + " has no default pool... skipping");
                    continue;
                }
                for (Pool pool : pools.values()) {
                    URLConnection poolConnection = urlConnBuilder(baseUri, pool.getUri());
                    String poolString = readToString(poolConnection);
                    configurationParser.loadPool(pool, poolString);
                    URLConnection poolBucketsConnection = urlConnBuilder(baseUri, pool.getBucketsUri());
                    String sBuckets = readToString(poolBucketsConnection);
                    Map<String, Bucket> bucketsForPool = configurationParser.parseBuckets(sBuckets);
                    pool.replaceBuckets(bucketsForPool);

                }
                boolean bucketFound = false;
                for (Pool pool : pools.values()) {
                    if (pool.hasBucket(bucketToFind)) {
                        bucketFound = true;
			break;
                    }
                }
                if (bucketFound) {
                    for (Pool pool : pools.values()) {
                        for (Map.Entry<String, Bucket> bucketEntry : pool.getROBuckets().entrySet()) {
                            this.buckets.put(bucketEntry.getKey(), bucketEntry.getValue());
                        }
                    }
                    this.loadedBaseUri = baseUri;
                    return;
                }
            } catch (ParseException e) {
                getLogger().warn("Provided URI " + baseUri + " has an unparsable response...skipping", e);
            } catch (IOException e) {
                getLogger().warn("Connection problems with URI " + baseUri + " ...skipping", e);
            }
            throw new ConfigurationException("Configuration for bucket " + bucketToFind + " was not found.");
        }
