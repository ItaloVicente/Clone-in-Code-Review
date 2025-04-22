    public void connect() throws Exception {
        if (ClusterDependentTest.minClusterVersion()[0] >= 5) {
            username = TestProperties.adminUser();
            password = TestProperties.adminPassword();
        }

