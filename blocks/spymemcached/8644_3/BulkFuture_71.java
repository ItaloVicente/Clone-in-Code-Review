  boolean isTimeout();

  V getSome(long timeout, TimeUnit unit) throws InterruptedException,
      ExecutionException;

  OperationStatus getStatus();
