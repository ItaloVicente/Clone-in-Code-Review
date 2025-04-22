  public void testNumVBuckets() throws Exception {
    if (TestConfig.isMembase()) {
      assert ((MembaseClient)client).getNumVBuckets() == 1024;
    }
  }

