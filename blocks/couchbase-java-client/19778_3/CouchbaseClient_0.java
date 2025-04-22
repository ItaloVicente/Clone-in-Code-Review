  public Future<Boolean> delete(String key,
          PersistTo req, ReplicateTo rep) {
    OperationFuture<Boolean> deleteOp = delete(key);
    try {
      if (deleteOp.get()) {
        observePoll(key, 0L, req, rep);
      }
    } catch (InterruptedException e) {
      deleteOp.set(false, deleteOp.getStatus());
    } catch (ExecutionException e) {
      deleteOp.set(false, deleteOp.getStatus());
    } catch (TimeoutException e) {
      deleteOp.set(false, deleteOp.getStatus());
    } catch (IllegalArgumentException e) {
      deleteOp.set(false, deleteOp.getStatus());
    } catch (RuntimeException e) {
      deleteOp.set(false, deleteOp.getStatus());
    }
    return (deleteOp);
  }
  public Future<Boolean> delete(String key, PersistTo req) {
    return delete(key, req, ReplicateTo.ZERO);
  }

