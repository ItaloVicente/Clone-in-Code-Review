      }
    }
    for (Operation op : ops) {
      if (op.isCancelled()) {
        status = new OperationStatus(false, "Cancelled");
        throw new ExecutionException(new RuntimeException("Cancelled"));
      }
      if (op.hasErrored()) {
        status = new OperationStatus(false, op.getException().getMessage());
        throw new ExecutionException(op.getException());
      }
