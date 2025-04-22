    boolean deleteStatus = false;

    try {
      deleteStatus = deleteOp.get();
    } catch (InterruptedException e) {
      deleteOp.set(false, new OperationStatus(false, "Delete get timed out"));
    } catch (ExecutionException e) {
      if(e.getCause() instanceof CancellationException) {
        deleteOp.set(false, new OperationStatus(false, "Delete get "
          + "cancellation exception "));
      } else {
        deleteOp.set(false, new OperationStatus(false, "Delete get "
          + "execution exception "));
      }
    }
    if (!deleteStatus) {
      return deleteOp;
    }
    try {
      observePoll(key, deleteOp.getCas(), req, rep, true);
      deleteOp.set(true, deleteOp.getStatus());
    } catch (ObservedException e) {
      deleteOp.set(false, new OperationStatus(false, e.getMessage()));
    } catch (ObservedTimeoutException e) {
      deleteOp.set(false, new OperationStatus(false, e.getMessage()));
    } catch (ObservedModifiedException e) {
      deleteOp.set(false, new OperationStatus(false, e.getMessage()));
    }
    return deleteOp;
