      if(e.getCause() instanceof CancellationException) {
        replaceOp.set(false, new OperationStatus(false, "Replace get "
          + "cancellation exception "));
      } else {
        replaceOp.set(false, new OperationStatus(false, "Replace get "
          + "execution exception "));
      }
