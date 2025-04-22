  public void testGATTimeout() throws Exception {
    if (TestConfig.isMembase()) {
      assertNull(client.get("gatkey"));
      assert client.set("gatkey", 1, "gatvalue").get().booleanValue();
      assert client.getAndTouch("gatkey", 2).getValue().equals("gatvalue");
      Thread.sleep(1300);
      assert client.get("gatkey").equals("gatvalue");
      Thread.sleep(2000);
      assertNull(client.getAndTouch("gatkey", 3));
    }
  }
