	public <T> Future<Boolean> asyncTouch(final String key, final int exp) {
		return asyncTouch(key, exp, transcoder);
	}

	public <T> Future<Boolean> asyncTouch(final String key, final int exp, 
			final Transcoder<T> tc) {
		final CountDownLatch latch=new CountDownLatch(1);
		final OperationFuture<Boolean> rv=new OperationFuture<Boolean>(latch, 
				operationTimeout);

		Operation op=opFact.touch(key, exp, new OperationCallback() {
			public void receivedStatus(OperationStatus status) {
				rv.set(status.isSuccess());
		}
			public void complete() {
				latch.countDown();
			}});
		rv.setOperation(op);
		addOp(key, op);
		return rv;
	}

