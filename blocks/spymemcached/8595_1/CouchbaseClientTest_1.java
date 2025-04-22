	@Test
	public void testQuerySetDescending() throws Exception {
		Query query = new Query();
		View view = client.getView(DESIGN_DOC_W_REDUCE, VIEW_NAME_W_REDUCE);
		ViewFuture future = client.query(view, query.setDescending(true));
		ViewResponseWithDocs response = future.get();
		assert response != null : future.getStatus();
	}

	@Test
	public void testQuerySetEndKeyDocID() throws Exception {
		Query query = new Query();
		View view = client.getView(DESIGN_DOC_W_REDUCE, VIEW_NAME_W_REDUCE);
		ViewFuture future = client.query(view, query.setEndkeyDocID("an_id"));
		ViewResponseWithDocs response = future.get();
		assert response != null : future.getStatus();
	}

	@Test
	public void testQuerySetGroup() throws Exception {
		Query query = new Query();
		View view = client.getView(DESIGN_DOC_W_REDUCE, VIEW_NAME_W_REDUCE);
		HttpFuture<ViewResponseReduced> future = client.queryAndReduce(view,
				query.setGroup(true));
		ViewResponseReduced response = future.get();
		assert response != null : future.getStatus();
	}

	@Test
	public void testQuerySetGroupWithLevel() throws Exception {
		Query query = new Query();
		View view = client.getView(DESIGN_DOC_W_REDUCE, VIEW_NAME_W_REDUCE);
		HttpFuture<ViewResponseReduced> future = client.queryAndReduce(view,
				query.setGroup(true, 1));
		ViewResponseReduced response = future.get();
		assert response != null : future.getStatus();
	}

	@Test
	public void testQuerySetInclusiveEnd() throws Exception {
		Query query = new Query();
		View view = client.getView(DESIGN_DOC_W_REDUCE, VIEW_NAME_W_REDUCE);
		ViewFuture future = client.query(view, query.setInclusiveEnd(true));
		ViewResponseWithDocs response = future.get();
		assert response != null : future.getStatus();
	}

	@Test
	public void testQuerySetKey() throws Exception {
		Query query = new Query();
		View view = client.getView(DESIGN_DOC_W_REDUCE, VIEW_NAME_W_REDUCE);
		ViewFuture future = client.query(view, query.setKey("a_key"));
		ViewResponseWithDocs response = future.get();
		assert response != null : future.getStatus();
	}

	@Test
	public void testQuerySetLimit() throws Exception {
		Query query = new Query();
		View view = client.getView(DESIGN_DOC_W_REDUCE, VIEW_NAME_W_REDUCE);
		ViewFuture future = client.query(view, query.setLimit(10));
		ViewResponseWithDocs response = future.get();
		assert response != null : future.getStatus();
	}

	@Test
	public void testQuerySetRange() throws Exception {
		Query query = new Query();
		View view = client.getView(DESIGN_DOC_W_REDUCE, VIEW_NAME_W_REDUCE);
		ViewFuture future = client.query(view, query.setRange("key0", "key2"));
		ViewResponseWithDocs response = future.get();
		assert response != null : future.getStatus();
	}

	@Test
	public void testQuerySetRangeStart() throws Exception {
		Query query = new Query();
		View view = client.getView(DESIGN_DOC_W_REDUCE, VIEW_NAME_W_REDUCE);
		ViewFuture future = client.query(view, query.setRangeStart("start"));
		ViewResponseWithDocs response = future.get();
		assert response != null : future.getStatus();
	}

	@Test
	public void testQuerySetRangeEnd() throws Exception {
		Query query = new Query();
		View view = client.getView(DESIGN_DOC_W_REDUCE, VIEW_NAME_W_REDUCE);
		ViewFuture future = client.query(view, query.setRangeEnd("end"));
		ViewResponseWithDocs response = future.get();
		assert response != null : future.getStatus();
	}

	@Test
	public void testQuerySetSkip() throws Exception {
		Query query = new Query();
		View view = client.getView(DESIGN_DOC_W_REDUCE, VIEW_NAME_W_REDUCE);
		ViewFuture future = client.query(view, query.setSkip(0));
		ViewResponseWithDocs response = future.get();
		assert response != null : future.getStatus();
	}

	@Test
	public void testQuerySetStale() throws Exception {
		Query query = new Query();
		View view = client.getView(DESIGN_DOC_W_REDUCE, VIEW_NAME_W_REDUCE);
		ViewFuture future = client.query(view, query.setStale(Stale.OK));
		ViewResponseWithDocs response = future.get();
		assert response != null : future.getStatus();
	}

	@Test
	public void testQuerySetStartkeyDocID() throws Exception {
		Query query = new Query();
		View view = client.getView(DESIGN_DOC_W_REDUCE, VIEW_NAME_W_REDUCE);
		ViewFuture future = client.query(view, query.setStartkeyDocID("key0"));
		ViewResponseWithDocs response = future.get();
		assert response != null : future.getStatus();
	}

	@Test
	public void testQuerySetUpdateSeq() throws Exception {
		Query query = new Query();
		View view = client.getView(DESIGN_DOC_W_REDUCE, VIEW_NAME_W_REDUCE);
		ViewFuture future = client.query(view, query.setUpdateSeq(true));
		ViewResponseWithDocs response = future.get();
		assert response != null : future.getStatus();
	}

