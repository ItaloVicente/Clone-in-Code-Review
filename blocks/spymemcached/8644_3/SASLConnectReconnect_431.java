    consoleHandler.setLevel(java.util.logging.Level.FINEST);

    m.verifySetAndGet();
    System.err.println("Pass one done.");
    Thread.sleep(60000);
    m.verifySetAndGet2(Integer.parseInt(args[3]));
    System.err.println("Pass two done.");

  }

  public void verifySetAndGet() {
    int iterations = 20;
    for (int i = 0; i < iterations; i++) {
      mc.set("test" + i, 0, "test" + i);
