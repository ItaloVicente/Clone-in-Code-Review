    try {
      deleteOp.get();
      deleteOp.set(true, deleteOp.getStatus());
      observePoll(key, 0L, req, rep);
    } catch (InterruptedException e) {
      deleteOp.set(false, deleteOp.getStatus());
    } catch (ExecutionException e) {
      deleteOp.set(false, deleteOp.getStatus());
    } catch (TimeoutException e) {
      deleteOp.set(false, deleteOp.getStatus());
    } catch (IllegalArgumentException e) {
      deleteOp.set(false, deleteOp.getStatus());
    } catch (RuntimeException e) {
