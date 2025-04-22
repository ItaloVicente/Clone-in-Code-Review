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

        if (this.authenticator == null) {
            throw new IllegalStateException("An Authenticator is required to perform cluster level querying");
        } else {
            try {
                List<Credential> creds = this.authenticator.getCredentials(CredentialContext.CLUSTER_N1QL, null);
                if (creds.isEmpty()) {
                    throw new IllegalStateException(
                            "CLUSTER_N1QL credentials are required in the Authenticator for cluster level querying");
                } else {
                    query.params().withCredentials(creds);
                    LOGGER.trace("Added {} credentials to a cluster-level N1qlQuery", creds.size());
                }
            } catch (IllegalArgumentException e) {
                throw new IllegalStateException("Couldn't retrieve credentials for cluster level querying from Authenticator", e);
            }
        }

        return delegateBucket.query(query);
    }

