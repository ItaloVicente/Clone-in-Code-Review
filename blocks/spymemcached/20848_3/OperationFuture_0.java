    if (cas == null) {
      try {
        get();
      } catch (InterruptedException e) {
        status = new OperationStatus(false, "Interrupted");
        Thread.currentThread().isInterrupted();
      } catch (ExecutionException e) {
        getLogger().warn("Error getting cas of operation", e);
      }
    }
    if (cas == null && status.isSuccess()) {
      throw new UnsupportedOperationException("This operation doesn't return"
          + "a cas value.");
