	public HttpFuture<ViewResponseReduced> queryAndReduce(final View view, final Query query){
		if (!view.hasReduce()) {
			throw new RuntimeException("This view doesn't contain a reduce function");
		}
		String uri = view.getURI() + query.getQueryString();
		final CountDownLatch couchLatch = new CountDownLatch(1);
		final HttpFuture<ViewResponseReduced> crv =
			new HttpFuture<ViewResponseReduced>(couchLatch, operationTimeout);

		final HttpRequest request = new BasicHttpRequest("GET", uri, HttpVersion.HTTP_1_1);
		final HttpOperationImpl op = new ReducedOperationImpl(request, new ReducedCallback() {
			ViewResponseReduced vr = null;
			@Override
			public void receivedStatus(OperationStatus status) {
				crv.set(vr, status);
			}
			@Override
			public void complete() {
				couchLatch.countDown();
			}
			@Override
			public void gotData(ViewResponseReduced response) {
				vr = response;
			}
		});
		crv.setOperation(op);
		addOp(op);
		return crv;
	}

