	public ViewFuture query(View view, Query query) {
		String queryString = query.toString();
		String params = (queryString.length() > 0) ? "&reduce=false" : "?reduce=false";

		String uri = view.getURI() + queryString + params;
		final CountDownLatch couchLatch = new CountDownLatch(1);
		final ViewFuture crv = new ViewFuture(couchLatch, operationTimeout);

		final HttpRequest request = new BasicHttpRequest("GET", uri, HttpVersion.HTTP_1_1);
		final HttpOperation op = new DocsOperationImpl(request, new DocsCallback() {
			ViewResponseWithDocs vr = null;
			@Override
			public void receivedStatus(OperationStatus status) {
				Collection<String> ids = new LinkedList<String>();
				Iterator<RowWithDocs> itr = vr.iterator();
				while (itr.hasNext()) {
					ids.add(itr.next().getId());
				}
				crv.set(vr, asyncGetBulk(ids), status);
			}
			@Override
			public void complete() {
				couchLatch.countDown();
			}
			@Override
			public void gotData(ViewResponseWithDocs response) {
				vr = response;
			}
		});
		crv.setOperation(op);
		addOp(op);
		return crv;
	}

	public HttpFuture<ViewResponseNoDocs> queryAndExcludeDocs(View view, Query query) {
		String queryString = query.toString();
		String params = (queryString.length() > 0) ? "&reduce=false" : "?reduce=false";
		params += "&include_docs=false";

		String uri = view.getURI() + queryString + params;
		final CountDownLatch couchLatch = new CountDownLatch(1);
		final HttpFuture<ViewResponseNoDocs> crv =
			new HttpFuture<ViewResponseNoDocs>(couchLatch, operationTimeout);

		final HttpRequest request = new BasicHttpRequest("GET", uri, HttpVersion.HTTP_1_1);
		final HttpOperation op = new NoDocsOperationImpl(request, new NoDocsOperation.NoDocsCallback() {
			ViewResponseNoDocs vr = null;
			@Override
			public void receivedStatus(OperationStatus status) {
				crv.set(vr, status);
			}
			@Override
			public void complete() {
				couchLatch.countDown();
			}
			@Override
			public void gotData(ViewResponseNoDocs response) {
				vr = response;
			}
		});
		crv.setOperation(op);
		addOp(op);
		return crv;
	}

	public HttpFuture<ViewResponseReduced> queryAndReduce(final View view, final Query query){
		if (!view.hasReduce()) {
			throw new RuntimeException("This view doesn't contain a reduce function");
		}
		String uri = view.getURI() + query.toString();
		final CountDownLatch couchLatch = new CountDownLatch(1);
		final HttpFuture<ViewResponseReduced> crv =
			new HttpFuture<ViewResponseReduced>(couchLatch, operationTimeout);

		final HttpRequest request = new BasicHttpRequest("GET", uri, HttpVersion.HTTP_1_1);
		final HttpOperation op = new ReducedOperationImpl(request, new ReducedCallback() {
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

