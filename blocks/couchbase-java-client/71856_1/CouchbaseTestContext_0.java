    public CouchbaseTestContext ignoreIfSearchIndexDoesNotExist(String idxname) {
        SearchQueryResult result = bucket.query(
            new SearchQuery(idxname, SearchQuery.queryString("test")).limit(1)
        );
        if (!result.status().isSuccess()) {
            try {
                result.hitsOrFail();
            } catch (IndexDoesNotExistException ex) {
                Assume.assumeTrue("FTS Index \"" + idxname + "\" not available.", false);
            }
        }
        return this;
    }


