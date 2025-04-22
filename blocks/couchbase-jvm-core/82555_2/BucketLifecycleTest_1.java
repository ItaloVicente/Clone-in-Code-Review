
        final OpenBucketRequest goodAttempt;
        if (ClusterDependentTest.minClusterVersion()[0] >= 5) {
            goodAttempt = new OpenBucketRequest(TestProperties.bucket(), TestProperties.adminUser(), TestProperties.adminPassword());
        } else {
            goodAttempt = new OpenBucketRequest(TestProperties.bucket(), TestProperties.username(), TestProperties.password());

        }
