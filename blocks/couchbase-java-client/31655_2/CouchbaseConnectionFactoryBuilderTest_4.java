
    int obsPollMax = new Long(CouchbaseConnectionFactory.DEFAULT_OBS_TIMEOUT
      / CouchbaseConnectionFactory.DEFAULT_OBS_POLL_INTERVAL).intValue();
    assertEquals(obsPollMax, connFact.getObsPollMax());
    assertEquals(CouchbaseConnectionFactory.DEFAULT_OBS_POLL_MAX,
      connFact.getObsPollMax());
