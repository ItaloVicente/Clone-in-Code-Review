  public void testParseBase() throws Exception {
    Map<String, Pool> base = configParser.parseBase(BASE_STRING);
    assertNotNull(base);
    assertTrue(!base.isEmpty());
    Pool pool = base.get(DEFAULT_POOL_NAME);
    assertNotNull(pool);
    assertEquals(DEFAULT_POOL_NAME, pool.getName());
    assertNotNull(pool.getUri());
  }
