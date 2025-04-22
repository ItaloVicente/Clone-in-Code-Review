  @Override
  public void testKeyWithSpaces() throws Exception {
    String key = "key with spaces";
    client.set(key, 0, "");
    assertNotNull("Couldn't get the key with spaces in it.", client.get(key));
  }

  @Override
  public void testKeyWithNewline() throws Exception {
    String key = "Key\n";
    client.set(key, 0, "");
    assertNotNull(client.get(key));
  }

  @Override
  public void testKeyWithReturn() throws Exception {
    String key = "Key\r";
    client.set(key, 0, "");
    assertNotNull(client.get(key));
  }

  @Override
  public void testKeyWithASCIINull() throws Exception {
    String key = "Key\0";
    client.set(key, 0, "");
    assertNotNull(client.get(key));
  }

  @Override
  public void testGetBulkKeyWSpaces() throws Exception {
    String key = "Key key2";
    client.set(key, 0, "");
    Map<String, Object> bulkReturn = client.getBulk(key);
    assertTrue(bulkReturn.size() >= 1);
  }

