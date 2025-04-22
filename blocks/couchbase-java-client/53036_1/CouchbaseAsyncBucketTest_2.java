    @Test
    public void shouldRetryPrepareAndQueryTwiceIfNameNotFound() {
        Statement st = select("*").from(i("beer-sample")).limit(10);
        PreparedPayload nonExistingPayload = new PreparedPayload(st, "nonExistingName");
        JsonObject error = JsonObject.create().put("code", 4040).put("msg", "nonExistingName");
        AsyncQueryResult mockResultNotFound = mock(AsyncQueryResult.class);
        when(mockResultNotFound.finalSuccess()).thenReturn(Observable.just(false));
        when(mockResultNotFound.errors()).thenReturn(Observable.just(error));

        AsyncQueryRow mockRow = mock(AsyncQueryRow.class);
        AsyncQueryResult mockResultFound = mock(AsyncQueryResult.class);
        when(mockResultFound.errors()).thenReturn(Observable.<JsonObject>empty());
        when(mockResultFound.rows()).thenReturn(Observable.just(mockRow).repeat(10));
        when(mockResultFound.finalSuccess()).thenReturn(Observable.just(true));

        CouchbaseAsyncBucket mockAsyncBucket = prepareMockForPreparedStatement(nonExistingPayload,
                mockResultNotFound, mockResultFound);

        AsyncQueryResult result = mockAsyncBucket.query(nonExistingPayload).toBlocking().first();
        List<JsonObject> errors = result.errors().toList().toBlocking().first();
        Boolean success = result.finalSuccess().toBlocking().first();
        List<AsyncQueryRow> allRows = result.rows().toList().toBlocking().first();

        assertTrue(success);
        assertTrue(errors.isEmpty());
        assertEquals(10, allRows.size());

        verify(mockAsyncBucket, times(2)).queryRaw(anyString());
        verify(mockAsyncBucket, times(1)).prepare(any(PrepareStatement.class));
    }

    @Test
    public void shouldQueryOnceAndNotReprepareIfNameFound() {
        Statement st = select("*").from(i("beer-sample")).limit(10);
        PreparedPayload existingPayload = new PreparedPayload(st, "existingName");

        AsyncQueryRow mockRow = mock(AsyncQueryRow.class);
        AsyncQueryResult mockResultFound = mock(AsyncQueryResult.class);
        when(mockResultFound.errors()).thenReturn(Observable.<JsonObject>empty());
        when(mockResultFound.rows()).thenReturn(Observable.just(mockRow).repeat(10));
        when(mockResultFound.finalSuccess()).thenReturn(Observable.just(true));

        CouchbaseAsyncBucket mockAsyncBucket = prepareMockForPreparedStatement(existingPayload, mockResultFound);

        AsyncQueryResult result = mockAsyncBucket.query(existingPayload).toBlocking().first();
        List<JsonObject> errors = result.errors().toList().toBlocking().first();
        Boolean success = result.finalSuccess().toBlocking().first();
        List<AsyncQueryRow> allRows = result.rows().toList().toBlocking().first();

        assertTrue(success);
        assertTrue(errors.isEmpty());
        assertEquals(10, allRows.size());

        verify(mockAsyncBucket, times(1)).queryRaw(anyString());
        verify(mockAsyncBucket, never()).prepare(any(Statement.class));
    }

    private CouchbaseAsyncBucket prepareMockForPreparedStatement(PreparedPayload payload,
            AsyncQueryResult firstQueryResult, AsyncQueryResult... otherQueryResults) {
        CouchbaseEnvironment env = DefaultCouchbaseEnvironment.builder()
                                                              .retryStrategy(BestEffortRetryStrategy.INSTANCE).build();

        Observable<AsyncQueryResult> firstResponse = Observable.just(firstQueryResult);
        Observable<AsyncQueryResult>[] otherResponses = new Observable[otherQueryResults.length];
        for (int i = 0; i < otherQueryResults.length; i++) {
            otherResponses[i] = Observable.just(otherQueryResults[i]);
        }

        CouchbaseAsyncBucket mockAsyncBucket = mock(CouchbaseAsyncBucket.class);
        when(mockAsyncBucket.environment()).thenReturn(env);
        when(mockAsyncBucket.query(any(Statement.class))).thenCallRealMethod();
        when(mockAsyncBucket.query(any(Query.class))).thenCallRealMethod();
        when(mockAsyncBucket.queryRaw(contains("\"prepared\":\"" + payload.preparedName() + "\"")))
                .thenReturn(firstResponse, otherResponses);
        when(mockAsyncBucket.prepare(any(Statement.class))).thenReturn(Observable.just(payload));
        when(mockAsyncBucket.prepare(anyString())).thenReturn(Observable.just(payload));

        return mockAsyncBucket;
    }

