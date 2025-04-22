        Bucket bucket1;
        Bucket bucket2;

        try {
            bucket1 = cluster.openBucket(TestProperties.bucket(), TestProperties.password());
            bucket2 = cluster.openBucket(TestProperties.bucket(), TestProperties.password());
        } catch (InvalidPasswordException ex) {
            cluster.authenticate(TestProperties.adminName(), TestProperties.adminPassword());
            bucket1 = cluster.openBucket(TestProperties.bucket());
            bucket2 = cluster.openBucket(TestProperties.bucket());
        }
