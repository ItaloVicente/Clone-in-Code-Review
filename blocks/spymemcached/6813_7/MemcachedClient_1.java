	public Future<CASValue<Object>> asyncGetAndTouch(final String key, final int exp) {
		return asyncGetAndTouch(key, exp, transcoder);
	}

	public <T> Future<CASValue<T>> asyncGetAndTouch(final String key, final int exp,
			final Transcoder<T> tc) {
		final CountDownLatch latch=new CountDownLatch(1);
		final OperationFuture<CASValue<T>> rv=new OperationFuture<CASValue<T>>(latch,
				operationTimeout);

		Operation op=opFact.getAndTouch(key, exp, new GetAndTouchOperation.Callback() {
			private CASValue<T> val=null;
			public void receivedStatus(OperationStatus status) {
				rv.set(val);
			}
			public void complete() {
				latch.countDown();
			}
			public void gotData(String key, int flags, long cas, byte[] data) {
				assert key.equals(key) : "Wrong key returned";
				assert cas > 0 : "CAS was less than zero:  " + cas;
				val=new CASValue<T>(cas, tc.decode(
					new CachedData(flags, data, tc.getMaxSize())));
			}});
		rv.setOperation(op);
		addOp(key, op);
		return rv;
	}

