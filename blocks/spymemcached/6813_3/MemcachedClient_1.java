	public Future<Object> asyncGetAndTouch(final String key, final int exp) {
		return asyncGetAndTouch(key, exp, transcoder);
	}

	public <T> GetFuture<T> asyncGetAndTouch(final String key, final int exp,
			final Transcoder<T> tc) {
		final CountDownLatch latch=new CountDownLatch(1);
		final GetFuture<T> rv=new GetFuture<T>(latch,
				operationTimeout);

		Operation op=opFact.gat(key, exp, new GATOperation.Callback() {
			private Future<T> val=null;
			public void receivedStatus(OperationStatus status) {
				rv.set(val);
			}
			public void complete() {
				latch.countDown();
			}
			public void gotData(String key, int flags, byte[] data) {
				assert key.equals(key) : "Wrong key returned";
				val=tcService.decode(tc,
					new CachedData(flags, data, tc.getMaxSize()));
			}});
		rv.setOperation(op);
		addOp(key, op);
		return rv;
	}

