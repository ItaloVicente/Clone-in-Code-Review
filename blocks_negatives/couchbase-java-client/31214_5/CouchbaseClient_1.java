    boolean setStatus = false;

    try {
      setStatus = setOp.get();
    } catch (InterruptedException e) {
      setOp.set(false, new OperationStatus(false, "Set get timed out"));
    } catch (ExecutionException e) {
      if(e.getCause() instanceof CancellationException) {
        setOp.set(false, new OperationStatus(false, "Set get "
          + "cancellation exception "));
      } else {
        setOp.set(false, new OperationStatus(false, "Set get "
          + "execution exception "));
      }
    }
    if (!setStatus) {
      return setOp;
    }
    try {
      observePoll(key, setOp.getCas(), req, rep, false);
      setOp.set(true, setOp.getStatus());
    } catch (ObservedException e) {
      setOp.set(false, new OperationStatus(false, e.getMessage()));
    } catch (ObservedTimeoutException e) {
      setOp.set(false, new OperationStatus(false, e.getMessage()));
    } catch (ObservedModifiedException e) {
      setOp.set(false, new OperationStatus(false, e.getMessage()));
    }
    return setOp;
