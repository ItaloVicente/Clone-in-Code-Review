    @Override
    public Observable<AsyncN1qlQueryResult> query(N1qlQuery query) {
        AsyncBucket delegateBucket = null;
        for (AsyncBucket asyncBucket : bucketCache.values()) {
            if (!asyncBucket.isClosed()) {
                delegateBucket = asyncBucket;
                break;
            }
        }
        if (delegateBucket == null) {
            return Observable.error(new UnsupportedOperationException("Cluster level querying is only available " +
                    "when at least 1 bucket is opened"));
        }

        if (this.authenticator != null) {
            try {
                List<Credential> creds = this.authenticator.getCredentials(CredentialContext.CLUSTER_N1QL, null);
                if (!creds.isEmpty()) {
                    query.params().withCredentials(creds);
                    LOGGER.trace("Added {} credentials to a cluster-level N1qlQuery", creds.size());
                }
            } catch (IllegalArgumentException e) {
            }
        }

        return delegateBucket.query(query);
    }

