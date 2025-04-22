      deleteOp.set(false, new OperationStatus(false, "Delete get "
              + "execution exception "));
    }
    if (!deleteStatus) {
      return deleteOp;
    }
    try {
      observePoll(key, 0x0L, req, rep);
      deleteOp.set(true, deleteOp.getStatus());
    } catch (ObservedException e) {
      deleteOp.set(false, new OperationStatus(false, e.getMessage()));
    } catch (ObservedTimeoutException e) {
      deleteOp.set(false, new OperationStatus(false, e.getMessage()));
    } catch (ObservedModifiedException e) {
      deleteOp.set(false, new OperationStatus(false, e.getMessage()));
