	public <T> CASValue<T> getAndLock(String key, int exp, Transcoder<T> tc) {
		try {
			return asyncGetAndLock(key, exp, tc).get(
					operationTimeout, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			throw new RuntimeException("Interrupted waiting for value", e);
		} catch (ExecutionException e) {
			throw new RuntimeException("Exception waiting for value", e);
		} catch (TimeoutException e) {
			throw new OperationTimeoutException("Timeout waiting for value", e);
		}
	}

	public CASValue<Object> getAndLock(String key, int exp) {
		return getAndLock(key, exp, transcoder);
	}

