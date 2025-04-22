  class BinaryConfigPoller implements Runnable {

    private static final int waitPeriod = 500;

    private int attempt;

    @Override
    public void run() {
      try {
        while (isBinary && getConfig().getConfig().isTainted()) {
          getLogger().debug("Polling for new carrier configuration and " +
            "waiting " + waitPeriod + "ms (Attempt " + ++attempt + ").");
          signalOutdated();
          try {
            Thread.sleep(waitPeriod);
          } catch (InterruptedException e) {
            getLogger().warn("Got interrupted while trying to poll for new " +
              "carrier config.", e);
            break;
          }
        }
      } finally {
        pollingBinary.set(false);
      }
    }
  }

