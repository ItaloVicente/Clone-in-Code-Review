	public <T> Boolean touch(String key, int exp, Transcoder<T> tc) {
		try {
			return asyncTouch(key, exp, tc).get(operationTimeout, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			throw new RuntimeException("Interrupted waiting for value", e);
		} catch (ExecutionException e) {
			throw new RuntimeException("Exception waiting for value", e);
		} catch (TimeoutException e) {
			throw new OperationTimeoutException("Timeout waiting for value", e);
		}
	}

	public Boolean touch(String key, int exp) {
		return touch(key, exp, transcoder);
	}

