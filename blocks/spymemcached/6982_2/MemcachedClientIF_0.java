	Future<CASValue<Object>> asyncGetAndLock(final String key, int exp);

	<T> Future<CASValue<T>> asyncGetAndLock(final String key, int exp,
			final Transcoder<T> tc);

	CASValue<Object> getAndLock(String key, int exp);

	<T> CASValue<T> getAndLock(String key, int exp, Transcoder<T> tc);

	Future<CASValue<Object>> asyncGetAndTouch(final String key, final int exp);

	<T> Future<CASValue<T>> asyncGetAndTouch(final String key, final int exp,
			final Transcoder<T> tc);

	CASValue<Object> getAndTouch(String key, int exp);

	<T> CASValue<T> getAndTouch(String key, int exp, Transcoder<T> tc);

