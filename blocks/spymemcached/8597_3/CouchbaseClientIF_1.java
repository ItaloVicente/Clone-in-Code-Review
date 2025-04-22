	public ViewFuture asyncQuery(View view, Query query);

	public HttpFuture<ViewResponseNoDocs> asyncQueryAndExcludeDocs(View view, Query query);

	public HttpFuture<ViewResponseReduced> asyncQueryAndReduce(View view, Query query);

	public ViewResponseWithDocs query(View view, Query query);
