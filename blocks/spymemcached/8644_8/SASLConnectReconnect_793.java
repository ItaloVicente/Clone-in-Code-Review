    for (int i = 0; i < iterations; i++) {
      Object res = mc.get("test" + i);
      assertEquals("test" + i, res);
    }
  }

  public void verifySetAndGet2(int iterations) {
    try {
      for (int i = 0; i <= iterations; i++) {
        mc.set("me" + i, 0, "me" + i);
      }

      for (int i = 0; i < iterations; i++) {
        try {
          Object res = mc.get("me" + i);
          if (res == null) {
            System.err.println("me" + i + " was not in the cache.");
          } else {
            assertEquals("me" + i, res);
          }
        } catch (OperationTimeoutException ex) {
          System.err.println("Operation timeed out, continuing.");
        }
      }
      mc.shutdown(1, TimeUnit.SECONDS);
    } catch (Exception ex) {
      System.err.println("Bailing out " + ex.toString() + "\n");
      ex.printStackTrace();
