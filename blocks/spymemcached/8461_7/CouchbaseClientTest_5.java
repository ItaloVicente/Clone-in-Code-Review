	@Test
	public void testViewDocsWithErrors() throws Exception {
		HttpOperation op = new DocsOperationImpl(null, new DocsCallback() {
			@Override
			public void receivedStatus(OperationStatus status) {
				assert status.isSuccess();
			}
			@Override
			public void complete() {
			}
			@Override
			public void gotData(ViewResponseWithDocs response) {
				assert response.getErrors().size() == 2;
				Iterator<RowError> row = response.getErrors().iterator();
				assert row.next().getFrom().equals("127.0.0.1:5984");
				assert response.size() == 0;
			}
		});
		HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, 200, "");
		String entityString = "{\"total_rows\":0,\"rows\":[],\"errors\": [{\"from\":" +
				"\"127.0.0.1:5984\",\"reason\":\"Design document `_design/testfoobar" +
				"` missing in database `test_db_b`.\"},{\"from\":\"http://localhost:5984" +
				"/_view_merge/\",\"reason\":\"Design document `_design/testfoobar`" +
				" missing in database `test_db_c`.\"}]}";
		StringEntity entity = new StringEntity(entityString);
		response.setEntity(entity);
		op.handleResponse(response);
	}

	@Test
	public void testViewNoDocsWithErrors() throws Exception {
		HttpOperation op = new NoDocsOperationImpl(null, new NoDocsCallback() {
			@Override
			public void receivedStatus(OperationStatus status) {
				assert status.isSuccess();
			}
			@Override
			public void complete() {
			}
			@Override
			public void gotData(ViewResponseNoDocs response) {
				assert response.getErrors().size() == 2;
				Iterator<RowError> row = response.getErrors().iterator();
				assert row.next().getFrom().equals("127.0.0.1:5984");
				assert response.size() == 0;
			}
		});
		HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, 200, "");
		String entityString = "{\"total_rows\":0,\"rows\":[],\"errors\": [{\"from\":" +
				"\"127.0.0.1:5984\",\"reason\":\"Design document `_design/testfoobar" +
				"` missing in database `test_db_b`.\"},{\"from\":\"http://localhost:5984" +
				"/_view_merge/\",\"reason\":\"Design document `_design/testfoobar`" +
				" missing in database `test_db_c`.\"}]}";
		StringEntity entity = new StringEntity(entityString);
		response.setEntity(entity);
		op.handleResponse(response);
	}

	@Test
	public void testViewReducedWithErrors() throws Exception {
		HttpOperation op = new ReducedOperationImpl(null, new ReducedCallback() {
			@Override
			public void receivedStatus(OperationStatus status) {
				assert status.isSuccess();
			}
			@Override
			public void complete() {
			}
			@Override
			public void gotData(ViewResponseReduced response) {
				assert response.getErrors().size() == 2;
				Iterator<RowError> row = response.getErrors().iterator();
				assert row.next().getFrom().equals("127.0.0.1:5984");
				assert response.size() == 0;
			}
		});
		HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, 200, "");
		String entityString = "{\"total_rows\":0,\"rows\":[],\"errors\": [{\"from\":" +
				"\"127.0.0.1:5984\",\"reason\":\"Design document `_design/testfoobar" +
				"` missing in database `test_db_b`.\"},{\"from\":\"http://localhost:5984" +
				"/_view_merge/\",\"reason\":\"Design document `_design/testfoobar`" +
				" missing in database `test_db_c`.\"}]}";
		StringEntity entity = new StringEntity(entityString);
		response.setEntity(entity);
		op.handleResponse(response);
	}
