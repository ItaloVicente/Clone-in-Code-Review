	Future<CASValue<Object>> asyncGetAndLock(final String key, int exp);

	<T> Future<CASValue<T>> asyncGetAndLock(final String key, int exp,
			final Transcoder<T> tc);

	CASValue<Object> getAndLock(String key, int exp);

	<T> CASValue<T> getAndLock(String key, int exp, Transcoder<T> tc);

