        Bucket bucket3;
        try {
            bucket3 = cluster.openBucket(TestProperties.bucket(), TestProperties.password());
        } catch(MixedAuthenticationException ex) {
            bucket3 = cluster.openBucket(TestProperties.bucket());
        }
