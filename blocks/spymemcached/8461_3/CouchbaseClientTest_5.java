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
				assert response.getErrors().size() == 1;
				Iterator<RowError> row = response.getErrors().iterator();
				assert row.next().getFrom().equals("http://10.2.1.14:5984/_view_merge/" +
						"?startkey=%22Mike%22");
				assert response.size() == 5;
			}
		});
		HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, 200, "");
		String entityString = "{\"total_rows\":6,\"rows\":[{\"error\":true,\"from\":" +
				"\"http://10.2.1.14:5984/_view_merge/?startkey=%22Mike%22\",\"reason\"" +
				":\"{\\\"error\\\":\\\"not_found\\\",\\\"reason\\\":\\\"missing\\\"}\"" +
				"},{\"id\":\"PerryKrug:vs:AaronMiller\",\"key\":\"PerryKrug:vs:" +
				"AaronMiller\",\"value\":null},{\"id\":\"PerryKrug:vs:BobWiederhold\"," +
				"\"key\":\"PerryKrug:vs:BobWiederhold\",\"value\":null},{\"id\":" +
				"\"PerryKrug:vs:DaleHarvey\",\"key\":\"PerryKrug:vs:DaleHarvey\",\"value" +
				"\":null},{\"id\":\"PerryKrug:vs:DamienKatz\",\"key\":\"PerryKrug:vs:" +
				"DamienKatz\",\"value\":null},{\"id\":\"PerryKrug:vs:DustinSallings\",\"key" +
				"\":\"PerryKrug:vs:DustinSallings\",\"value\":null}]}";
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
				assert response.getErrors().size() == 1;
				Iterator<RowError> row = response.getErrors().iterator();
				assert row.next().getFrom().equals("http://10.2.1.14:5984/_view_merge/" +
						"?startkey=%22Mike%22");
				assert response.size() == 5;
			}
		});
		HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, 200, "");
		String entityString = "{\"total_rows\":6,\"rows\":[{\"error\":true,\"from\":" +
				"\"http://10.2.1.14:5984/_view_merge/?startkey=%22Mike%22\",\"reason\"" +
				":\"{\\\"error\\\":\\\"not_found\\\",\\\"reason\\\":\\\"missing\\\"}\"" +
				"},{\"id\":\"PerryKrug:vs:AaronMiller\",\"key\":\"PerryKrug:vs:" +
				"AaronMiller\",\"value\":null},{\"id\":\"PerryKrug:vs:BobWiederhold\"," +
				"\"key\":\"PerryKrug:vs:BobWiederhold\",\"value\":null},{\"id\":" +
				"\"PerryKrug:vs:DaleHarvey\",\"key\":\"PerryKrug:vs:DaleHarvey\",\"value" +
				"\":null},{\"id\":\"PerryKrug:vs:DamienKatz\",\"key\":\"PerryKrug:vs:" +
				"DamienKatz\",\"value\":null},{\"id\":\"PerryKrug:vs:DustinSallings\",\"key" +
				"\":\"PerryKrug:vs:DustinSallings\",\"value\":null}]}";
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
				assert response.getErrors().size() == 1;
				Iterator<RowError> row = response.getErrors().iterator();
				assert row.next().getFrom().equals("http://10.2.1.14:5984/_view_merge/" +
						"?startkey=%22Mike%22");
				assert response.size() == 5;
			}
		});
		HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, 200, "");
		String entityString = "{\"total_rows\":6,\"rows\":[{\"error\":true,\"from\":" +
				"\"http://10.2.1.14:5984/_view_merge/?startkey=%22Mike%22\",\"reason\"" +
				":\"{\\\"error\\\":\\\"not_found\\\",\\\"reason\\\":\\\"missing\\\"}\"" +
				"},{\"key\":\"PerryKrug:vs:AaronMiller\",\"value\":null},{\"key\":" +
				"\"PerryKrug:vs:BobWiederhold\",\"value\":null},{\"key\":\"PerryKrug:" +
				"vs:DaleHarvey\",\"value\":null},{\"key\":\"PerryKrug:vs:DamienKatz\"," +
				"\"value\":null},{\"key\":\"PerryKrug:vs:DustinSallings\",\"value\":null}]}";
		StringEntity entity = new StringEntity(entityString);
		response.setEntity(entity);
		op.handleResponse(response);
	}
