    BucketConfigurationProvider provider = new BucketConfigurationProvider(
      seedNodes,
      bucket,
      password,
      new CouchbaseConnectionFactory(seedNodes, bucket, password)
    );

    assertFalse(provider.bootstrapBinary());
  }

  @Test
  public void shouldSkipHttpOnManualDisable() throws Exception {
    System.setProperty("cbclient.disableHttpBootstrap", "true");

    BucketConfigurationProvider provider = new BucketConfigurationProvider(
      seedNodes,
      bucket,
      password,
      new CouchbaseConnectionFactory(seedNodes, bucket, password)
    );

    assertFalse(provider.bootstrapHttp());
  }
