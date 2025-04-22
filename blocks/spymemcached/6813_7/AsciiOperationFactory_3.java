	public GetAndTouchOperation getAndTouch(String key, int expiration,
			GetAndTouchOperation.Callback cb) {
		throw new UnsupportedOperationException("Get and Touch not supported " +
				"with ascii protocol");
	}

