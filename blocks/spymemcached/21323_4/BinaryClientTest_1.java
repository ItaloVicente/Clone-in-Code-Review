  @Override
  public void testInvalidKey1() throws Exception {
    client.get("key with spaces");
  }

  @Override
  public void testInvalidKey3() throws Exception {
    client.get("Key\n");
  }

  @Override
  public void testInvalidKey4() throws Exception {
    client.get("Key\r");
  }

  @Override
  public void testInvalidKey5() throws Exception {
    client.get("Key\0");
  }

  @Override
  public void testInvalidKeyBulk() throws Exception {
    client.getBulk("Key key2");
  }

