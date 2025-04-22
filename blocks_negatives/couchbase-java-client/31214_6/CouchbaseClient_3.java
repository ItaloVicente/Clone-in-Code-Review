    try {
      replaceStatus = replaceOp.get();
    } catch (InterruptedException e) {
      replaceOp.set(false, new OperationStatus(false, "Replace get timed out"));
    } catch (ExecutionException e) {
      if(e.getCause() instanceof CancellationException) {
        replaceOp.set(false, new OperationStatus(false, "Replace get "
          + "cancellation exception "));
      } else {
        replaceOp.set(false, new OperationStatus(false, "Replace get "
          + "execution exception "));
