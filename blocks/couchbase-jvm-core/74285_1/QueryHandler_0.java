        createParser();
    }

    private void createParser() {
        String v2Enabled = System.getProperty("com.couchbase.enableNewQueryResponseParser");
        if (v2Enabled == null) {
            parser = new QueryResponseParserV1(env().scheduler(), env().autoreleaseAfter());
        } else {
            parser = new QueryResponseParserV2(env().scheduler(), env().autoreleaseAfter());
        }
