  @Override
  public <T> CASValue<T> getsFromReplica(String key, Transcoder<T> tc) {
    try {
      return asyncGetsFromReplica(key, tc).get(operationTimeout,
        TimeUnit.MILLISECONDS);
    } catch (InterruptedException e) {
      throw new RuntimeException("Interrupted waiting for value", e);
    } catch (ExecutionException e) {
      throw new RuntimeException("Exception waiting for value", e);
    } catch (TimeoutException e) {
      throw new OperationTimeoutException("Timeout waiting for value", e);
    }
  }

