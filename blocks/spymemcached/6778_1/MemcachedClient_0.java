	public <T> Future<T> asyncGetl(final String key, int exp, final Transcoder<T> tc) {

		final CountDownLatch latch=new CountDownLatch(1);
		final GetFuture<T> rv=new GetFuture<T>(latch, operationTimeout);

		Operation op=opFact.getl(key, exp,
				new GetlOperation.Callback() {
			private Future<T> val=null;
			public void receivedStatus(OperationStatus status) {
				rv.set(val);
			}
			public void gotData(String k, int flags, byte[] data) {
				assert key.equals(k) : "Wrong key returned";
				val=tcService.decode(tc,
						new CachedData(flags, data, tc.getMaxSize()));
			}
			public void complete() {
				latch.countDown();
			}});
		rv.setOperation(op);
		addOp(key, op);
		return rv;
	}
		
	public Future<Object> asyncGetl(final String key, int exp) {
		return asyncGetl(key, exp, transcoder);
	}	

