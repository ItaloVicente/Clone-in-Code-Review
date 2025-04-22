    TestAdmin testAdmin = new TestAdmin(TestConfig.IPV4_ADDR,
              CbTestConfig.CLUSTER_ADMINNAME,
              CbTestConfig.CLUSTER_PASS,
              "default",
              "");
    TestAdmin.reCreateDefaultBucket();

    initClient(new CouchbaseConnectionFactory(
            Arrays.asList(URI.create("http://"
          + TestConfig.IPV4_ADDR + ":8091/pools")), "default", ""));
