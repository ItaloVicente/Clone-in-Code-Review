    boolean addStatus = false;

    try {
      addStatus = addOp.get();
    } catch (InterruptedException e) {
      addOp.set(false, new OperationStatus(false, "Add get timed out"));
    } catch (ExecutionException e) {
      if(e.getCause() instanceof CancellationException) {
        addOp.set(false, new OperationStatus(false, "Add get "
          + "cancellation exception "));
      } else {
        addOp.set(false, new OperationStatus(false, "Add get "
          + "execution exception "));
      }
    }
    if (!addStatus) {
      return addOp;
    }
    try {
      observePoll(key, addOp.getCas(), req, rep, false);
      addOp.set(true, addOp.getStatus());
    } catch (ObservedException e) {
      addOp.set(false, new OperationStatus(false, e.getMessage()));
    } catch (ObservedTimeoutException e) {
      addOp.set(false, new OperationStatus(false, e.getMessage()));
    } catch (ObservedModifiedException e) {
      addOp.set(false, new OperationStatus(false, e.getMessage()));
    }
    return addOp;
