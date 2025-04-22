  <T> OperationFuture<Boolean> asyncUnlock(final String key,
          long casId, final Transcoder<T> tc);

  OperationFuture<Boolean> asyncUnlock(final String key,
          long casId);

  <T> Boolean unlock(final String key,
          long casId, final Transcoder<T> tc);

  Boolean unlock(final String key,
          long casId);

