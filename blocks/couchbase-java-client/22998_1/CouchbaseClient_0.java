  public OperationFuture<Boolean> delete(String key,
          PersistTo req, ReplicateTo rep) {

    OperationFuture<Boolean> deleteOp = delete(key);
    boolean deleteStatus = false;

    try {
      deleteStatus = deleteOp.get();
    } catch (InterruptedException e) {
      deleteOp.set(false, new OperationStatus(false, "Delete get timed out"));
    } catch (ExecutionException e) {
      deleteOp.set(false, new OperationStatus(false, "Delete get "
              + "execution exception "));
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
  }
  public OperationFuture<Boolean> delete(String key, PersistTo req) {
    return delete(key, req, ReplicateTo.ZERO);
  }

