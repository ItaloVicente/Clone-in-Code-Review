      if(e.getCause() instanceof CancellationException) {
        deleteOp.set(false, new OperationStatus(false, "Delete get "
          + "cancellation exception "));
      } else {
        deleteOp.set(false, new OperationStatus(false, "Delete get "
          + "execution exception "));
      }
