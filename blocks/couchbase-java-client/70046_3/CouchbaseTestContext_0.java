    public CouchbaseTestContext ignoreIfSearchServiceNotFound() {
        try {
            this.bucket().query(new SearchQuery(this.bucketName, SearchQuery.matchPhrase("deadbeef")));
        } catch (Exception ex) {
            Assume.assumeTrue("Query service not available", (ex instanceof ServiceNotAvailableException) == false);
        }
        return this;
    }

