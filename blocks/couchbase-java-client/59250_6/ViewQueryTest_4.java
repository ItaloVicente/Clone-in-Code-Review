
    @Test
    public void shouldFlagOrderRetainedWhenUsingIncludeDocsOrdered() {
        ViewQuery query1 = ViewQuery.from("a", "b").includeDocsOrdered();
        ViewQuery query2 = ViewQuery.from("a", "b").includeDocsOrdered(true);
        ViewQuery query3 = ViewQuery.from("a", "b").includeDocsOrdered(JsonDocument.class);
        ViewQuery query4 = ViewQuery.from("a", "b").includeDocsOrdered(true, JsonDocument.class);

        assertEquals(true, query1.isOrderRetained());
        assertEquals(true, query2.isOrderRetained());
        assertEquals(true, query3.isOrderRetained());
        assertEquals(true, query4.isOrderRetained());
    }


    @Test
    public void shouldDeactivateOrderRetainedWhenSettingIncludeDocsOrderedToFalse() {
        ViewQuery query1 = ViewQuery.from("a", "b").includeDocsOrdered();
        assertEquals(true, query1.isOrderRetained());
        query1.includeDocsOrdered(false);
        assertEquals(false, query1.isOrderRetained());

        ViewQuery query2 = ViewQuery.from("a", "b").includeDocsOrdered(true, JsonDocument.class);
        assertEquals(true, query2.isOrderRetained());
        query2.includeDocsOrdered(false, JsonDocument.class);
        assertEquals(false, query2.isOrderRetained());
    }


    @Test
    public void shouldLoadDocumentsOutOfOrderWithIncludeDocs() {
        StringBuilder trace = new StringBuilder();
        Bucket bucket = mockDelayedBucket(2, trace, "A", "B", "C", "D");
        ViewResult result = bucket.query(ViewQuery.from("any", "view")
                .includeDocs());

        String[] expected = new String[]{"C", "D", "A", "B"};
        String expectedTrace = "\nGET A\nGET B\nGET C\nGot C\nGET D\nGot D\nDelayed A by 100ms\nGot A\nDelayed B by 200ms\nGot B";

        assertOrder(expected, expectedTrace, result.allRows(), trace.toString());
    }

    @Test
    public void shouldLoadDocumentsInOrderWithIncludeDocsOrdered() {
        StringBuilder trace = new StringBuilder();
        Bucket bucket = mockDelayedBucket(2, trace, "A", "B", "C", "D");
        ViewResult result = bucket.query(ViewQuery.from("any", "view")
                .includeDocsOrdered());

        String[] expectedIds = new String[]{"A", "B", "C", "D"};
        String expectedTrace = "\nGET A\nGET B\nGET C\nGot C\nGET D\nGot D\nDelayed A by 100ms\nGot A\nDelayed B by 200ms\nGot B";
        assertOrder(expectedIds, expectedTrace, result.allRows(), trace.toString());
    }

    private void assertOrder(String[] expectedIds, String expectedTrace, List<ViewRow> rows, String trace) {
        for (int i = 0; i < rows.size(); i++) {
            ViewRow row = rows.get(i);
            assertNotNull(row);
            JsonDocument doc = row.document();
            assertEquals(row.id(), doc.id());
            assertEquals(expectedIds[i], row.id());
        }

        assertEquals(expectedTrace, trace);
    }

    private Bucket mockDelayedBucket(final int numberDelayed, final StringBuilder trace, final String... keys) {
        final Set<String> delayed = new HashSet<String>(numberDelayed);
        delayed.addAll(Arrays.asList(keys).subList(0, numberDelayed));

        List<ByteBuf> fakeRows = new ArrayList<ByteBuf>(keys.length);
        for (String key : keys) {
            String fakeRowJson = JsonObject.create()
                    .put("id", key)
                    .toString();
            ByteBuf fakeBuffer = Unpooled.copiedBuffer(fakeRowJson, CharsetUtil.UTF_8);
            fakeRows.add(fakeBuffer);
        }
        final Observable fakeRowObs = Observable.from(fakeRows);
        final AtomicInteger delay = new AtomicInteger(100);

        final AsyncBucket spyBucket = Mockito.mock(AsyncBucket.class);

        when(spyBucket.get(Matchers.anyString(), any(Class.class))).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String key = (String) invocation.getArguments()[0];
                Observable<JsonDocument> obs = Observable.just(JsonDocument.create(key))
                        .doOnNext(new Action1<JsonDocument>() {
                            @Override
                            public void call(JsonDocument jsonDocument) {
                                trace.append("\nGET ").append(jsonDocument.id());
                            }
                        });
                if (delayed.contains(key)) {
                    final int d = delay.getAndAdd(100);
                    obs = obs.delay(d, TimeUnit.MILLISECONDS)
                            .doOnNext(new Action1<JsonDocument>() {
                                @Override
                                public void call(JsonDocument jsonDocument) {
                                    trace.append("\nDelayed ").append(jsonDocument.id()).append(" by ").append(d).append("ms");
                                }
                            });
                }
                return obs.doOnNext(new Action1<JsonDocument>() {
                    @Override
                    public void call(JsonDocument jsonDocument) {
                        trace.append("\nGot ").append(jsonDocument.id());
                    }
                });
            }
        });

        when(spyBucket.query(any(ViewQuery.class))).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                final ViewQuery query = (ViewQuery) invocation.getArguments()[0];

                ViewQueryResponse response = new ViewQueryResponse(fakeRowObs, Observable.<ByteBuf>empty(),
                        Observable.<String>empty(), 0, "", ResponseStatus.SUCCESS, null);

                return Observable.just(response)
                        .flatMap(new Func1<ViewQueryResponse, Observable<AsyncViewResult>>() {
                            @Override
                            public Observable<AsyncViewResult> call(final ViewQueryResponse response) {
                                return ViewQueryResponseMapper.mapToViewResult(spyBucket, query, response);
                            }
                        });
            }
        });
        return new CouchbaseBucket(spyBucket, DefaultCouchbaseEnvironment.create(), null, "", "");
    }
