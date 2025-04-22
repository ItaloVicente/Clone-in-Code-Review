        try {
            bucket = cluster.openBucket(TestProperties.bucket(), TestProperties.password());
        } catch (InvalidPasswordException ex) {
            cluster.authenticate(TestProperties.adminName(), TestProperties.adminPassword());
            bucket = cluster.openBucket(TestProperties.bucket());
        }
