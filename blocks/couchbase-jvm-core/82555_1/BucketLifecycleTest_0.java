        OpenBucketRequest request;
        if (ClusterDependentTest.minClusterVersion()[0] >= 5) {
            request = new OpenBucketRequest(TestProperties.bucket(), TestProperties.adminUser(), TestProperties.adminPassword());
        } else {
            request = new OpenBucketRequest(TestProperties.bucket(), TestProperties.username(), TestProperties.password());
        }
