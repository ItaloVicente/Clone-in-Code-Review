    } else {
      LOGGER.log(Level.FINE, "No reconnect required, though check requested."
              + " Current config check is {0} out of a threshold of {1}.",
              new Object[]{configThresholdCount, maxConfigCheck});
    }
  }

  /**
   * Checks if there have been more requests than allowed through
   * maxConfigCheck in a 10 second period.
   *
   * If this is the case, then true is returned. If the timeframe between
   * two distinct requests is more than 10 seconds, a fresh timeframe starts.
   * This means that 10 calls every second would trigger an update while
   * 1 operation, then a 11 second sleep and one more operation would not.
   *
   * @return true if there were more config check requests than maxConfigCheck
   *              in the 10 second period.
   */
  protected boolean pastReconnThreshold() {
    long currentTime = System.nanoTime();

    if (currentTime - thresholdLastCheck >= TimeUnit.SECONDS.toNanos(10)) {
      configThresholdCount.set(0);
      thresholdLastCheck = currentTime;
    }

    if (configThresholdCount.incrementAndGet() >= maxConfigCheck) {
      return true;
    }

    return false;
