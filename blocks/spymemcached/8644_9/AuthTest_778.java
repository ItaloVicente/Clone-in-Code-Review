  public static void main(String[] a) throws Exception {
    AuthTest lt = new AuthTest("testuser", "testpass");
    lt.init();
    long start = System.currentTimeMillis();
    try {
      lt.run();
    } finally {
      lt.shutdown();
    }
    long end = System.currentTimeMillis();
    System.out.println("Runtime:  " + (end - start) + "ms");
  }
