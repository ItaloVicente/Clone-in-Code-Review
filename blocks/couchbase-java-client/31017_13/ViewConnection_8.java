    ioReactor.shutdown();
    try {
      reactorThread.join(0);
    } catch (InterruptedException ex) {
      getLogger().error("Interrupt " + ex + " received while waiting for "
        + "view thread to shut down.");
