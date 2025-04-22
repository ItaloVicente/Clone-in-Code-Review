  private ObserveFuture<Boolean> asyncObserveStore(final String key,
    final OperationFuture<Boolean> original, final PersistTo req,
    final ReplicateTo rep, final String prefix, final boolean delete) {

    final CountDownLatch latch = new CountDownLatch(1);
    final ObserveFuture<Boolean> observeFuture = new ObserveFuture<Boolean>(
      key, latch, operationTimeout, executorService);

    original.addListener(new OperationCompletionListener() {
      @Override
      public void onComplete(final OperationFuture<?> future) throws Exception {
        boolean replaceStatus = false;

        try {
          replaceStatus = (Boolean) future.get();
          observeFuture.set(replaceStatus, future.getStatus());
          observeFuture.setCas(future.getCas());
        } catch (InterruptedException e) {
          observeFuture.set(false, new OperationStatus(false, prefix + " get "
            + "timed out"));
        } catch (ExecutionException e) {
          if(e.getCause() instanceof CancellationException) {
            observeFuture.set(false, new OperationStatus(false, prefix + " get "
              + "cancellation exception "));
          } else {
            observeFuture.set(false, new OperationStatus(false, prefix + " get "
              + "execution exception "));
          }
        }

        if (!replaceStatus) {
          latch.countDown();
          return;
        }

        try {
          observePoll(key, future.getCas(), req, rep, delete);
          observeFuture.set(true, future.getStatus());
        } catch (ObservedException e) {
          observeFuture.set(false, new OperationStatus(false, e.getMessage()));
        } catch (ObservedTimeoutException e) {
          observeFuture.set(false, new OperationStatus(false, e.getMessage()));
        } catch (ObservedModifiedException e) {
          observeFuture.set(false, new OperationStatus(false, e.getMessage()));
        }

        latch.countDown();
