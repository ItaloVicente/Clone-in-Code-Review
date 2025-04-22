    boolean success = false;
    try {
      locator.getPrimary("key1");
    } catch(RuntimeException e) {
      success = true;
    }
    assertTrue("No RuntimeException thrown when vbucket master is -1", success);
