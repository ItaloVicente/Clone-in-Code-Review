      if(e.getCause() instanceof CancellationException) {
        setOp.set(false, new OperationStatus(false, "Set get "
          + "cancellation exception "));
      } else {
        setOp.set(false, new OperationStatus(false, "Set get "
          + "execution exception "));
      }
