    public CouchbaseTestContext ensurePrimaryIndex() {
        if (env.queryEnabled() || clusterManager.info().checkAvailable(CouchbaseFeature.N1QL)) {
            N1qlQueryResult result = bucket().query(
                    N1qlQuery.simple("CREATE PRIMARY INDEX ON `" + bucketName() + "`",
                            N1qlParams.build().consistency(ScanConsistency.REQUEST_PLUS)), 2, TimeUnit.MINUTES);
            if (!result.finalSuccess()) {
                for (JsonObject error : result.errors()) {
                    if (!error.getString("msg").contains("already exist")) {
                        throw new CouchbaseException("Could not CREATE PRIMARY INDEX - " + result.errors().toString());
                    }
                }
            }
        }
        return this;
    }

