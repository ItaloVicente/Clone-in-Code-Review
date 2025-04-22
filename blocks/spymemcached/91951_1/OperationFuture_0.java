  
  public OperationStatus getStatus(long duration, TimeUnit units) {
    if (status == null) {
      try {
        get(duration, units);
      } catch (InterruptedException e) {
        status = new OperationStatus(false, "Interrupted", StatusCode.INTERRUPTED);
      } catch (ExecutionException e) {
        getLogger().warn("Error getting status of operation", e);
      }
    }
    return status;
  }
