	public <T> Future<CASValue<T>> asyncGetAndLock(final String key, int exp,
			final Transcoder<T> tc) {
		final CountDownLatch latch=new CountDownLatch(1);
		final OperationFuture<CASValue<T>> rv=
			new OperationFuture<CASValue<T>>(latch, operationTimeout);

		Operation op=opFact.getl(key, exp,
				new GetlOperation.Callback() {
			private CASValue<T> val=null;
			public void receivedStatus(OperationStatus status) {
				rv.set(val);
			}
			public void gotData(String k, int flags, long cas, byte[] data) {
				assert key.equals(k) : "Wrong key returned";
				assert cas > 0 : "CAS was less than zero:  " + cas;
				val=new CASValue<T>(cas, tc.decode(
					new CachedData(flags, data, tc.getMaxSize())));
			}
			public void complete() {
				latch.countDown();
			}});
		rv.setOperation(op);
		addOp(key, op);
		return rv;
	}

	public Future<CASValue<Object>> asyncGetAndLock(final String key, int exp) {
		return asyncGetAndLock(key, exp, transcoder);
	}

