  public <T> BulkFuture<Map<String, T>> asyncGetBulk(Collection<String> keys,
          Iterator<Transcoder<T>> tcIter) {
    return asyncGetBulk(keys.iterator(), tcIter);
  }

  public <T> BulkFuture<Map<String, T>> asyncGetBulk(Iterator<String> keyIter,
      Transcoder<T> tc) {
    return asyncGetBulk(keyIter,
            new SingleElementInfiniteIterator<Transcoder<T>>(tc));
  }

