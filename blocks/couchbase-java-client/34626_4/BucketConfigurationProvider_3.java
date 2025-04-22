
      boolean result = latch.await(5, TimeUnit.SECONDS);
      if (!result) {
        throw new IOException("Connection could not be established to carrier"
          + " port in the given time interval.");
      }
