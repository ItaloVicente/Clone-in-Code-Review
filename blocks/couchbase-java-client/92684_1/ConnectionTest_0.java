        try {
            cluster.openBucket(TestProperties.bucket(), TestProperties.password());
        } catch (InvalidPasswordException ex) {
            cluster.authenticate(TestProperties.adminName(), TestProperties.adminPassword());
            cluster.openBucket(TestProperties.bucket());
        }
