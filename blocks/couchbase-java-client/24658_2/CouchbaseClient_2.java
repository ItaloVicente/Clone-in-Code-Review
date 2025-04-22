      if(e.getCause() instanceof CancellationException) {
        addOp.set(false, new OperationStatus(false, "Add get "
          + "cancellation exception "));
      } else {
        addOp.set(false, new OperationStatus(false, "Add get "
          + "execution exception "));
      }
