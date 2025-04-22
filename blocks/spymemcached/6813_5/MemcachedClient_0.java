	public <T> T getAndTouch(String key, int exp, Transcoder<T> tc) {
		try {
			return asyncGetAndTouch(key, exp, tc).get(operationTimeout,
					TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			throw new RuntimeException("Interrupted waiting for value", e);
		} catch (ExecutionException e) {
			throw new RuntimeException("Exception waiting for value", e);
		} catch (TimeoutException e) {
			throw new OperationTimeoutException("Timeout waiting for value", e);
		}
	}

	public Object getAndTouch(String key, int exp) {
		return getAndTouch(key, exp, transcoder);
	}

