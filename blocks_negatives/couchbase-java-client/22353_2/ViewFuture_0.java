
    if (!latch.await(duration, units)) {
      if (op != null) {
        op.timeOut();
      }
      status = new OperationStatus(false, "Timed out");
      throw new TimeoutException("Timed out waiting for operation");
    }

    if (op != null && op.hasErrored()) {
      status = new OperationStatus(false, op.getException().getMessage());
      throw new ExecutionException(op.getException());
    }


    if (op != null && op.isCancelled()) {
      status = new OperationStatus(false, "Operation Cancelled");
      throw new ExecutionException(new RuntimeException("Cancelled"));
    }

    if (op != null && op.isTimedOut()) {
      status = new OperationStatus(false, "Timed out");
      throw new ExecutionException(new CheckedOperationTimeoutException(
          "Operation timed out.", (Operation)op));
    }
