                    boolean parseSuccess = response.status().isSuccess();
                    String contextId = response.clientRequestId() == null ? "" : response.clientRequestId();
                    String requestId = response.requestId();

                    AsyncQueryResult r = new DefaultAsyncQueryResult(rows, signature, info, errors,
                            finalSuccess, parseSuccess, requestId, contextId);
                    return Observable.just(r);
                }
            });
    }

    @Override
    public Observable<QueryPlan> prepare(String statement) {
        return prepare(PrepareStatement.prepare(statement));
    }

    @Override
    public Observable<QueryPlan> prepare(Statement statement) {
        Statement prepared = statement instanceof PrepareStatement ? statement : PrepareStatement.prepare(statement);
        SimpleQuery query = Query.simple(prepared);

        GenericQueryRequest prepareRequest = GenericQueryRequest.jsonQuery(query.n1ql().toString(),
            bucket, password);
        return core
            .<GenericQueryResponse>send(prepareRequest)
            .flatMap(new Func1<GenericQueryResponse, Observable<QueryPlan>>() {
                @Override
                public Observable<QueryPlan> call(GenericQueryResponse r) {
                    if (r.status().isSuccess()) {
                        r.info().subscribe(Buffers.BYTE_BUF_RELEASER);
                        r.signature().subscribe(Buffers.BYTE_BUF_RELEASER);
                        r.errors().subscribe(Buffers.BYTE_BUF_RELEASER);
                        return r.rows().map(new Func1<ByteBuf, QueryPlan>() {
                            @Override
                            public QueryPlan call(ByteBuf byteBuf) {
                                try {
                                    JsonObject value = JSON_OBJECT_TRANSCODER.byteBufToJsonObject(byteBuf);
                                    return new QueryPlan(value);
                                } catch (Exception e) {
                                    throw new TranscodingException("Could not decode N1QL Query Plan.", e);
                                } finally {
                                    byteBuf.release();
                                }
                            }
                        });
                    } else {
                        r.info().subscribe(Buffers.BYTE_BUF_RELEASER);
                        r.signature().subscribe(Buffers.BYTE_BUF_RELEASER);
                        r.rows().subscribe(Buffers.BYTE_BUF_RELEASER);
                        return r.errors().map(new Func1<ByteBuf, Exception>() {
                            @Override
                            public Exception call(ByteBuf byteBuf) {
                                try {
                                    JsonObject value = JSON_OBJECT_TRANSCODER.byteBufToJsonObject(byteBuf);
                                    return new CouchbaseException("Query Error - " + value.toString());
                                } catch (Exception e) {
                                    throw new TranscodingException("Could not decode N1QL Query Plan.", e);
                                } finally {
                                    byteBuf.release();
                                }
                            }
                        }).reduce(new ArrayList<Throwable>(),
                                new Func2<ArrayList<Throwable>, Exception, ArrayList<Throwable>>() {
                                    @Override
                                    public ArrayList<Throwable> call(ArrayList<Throwable> throwables,
                                            Exception error) {
                                        throwables.add(error);
                                        return throwables;
                                    }
                                }).flatMap(new Func1<ArrayList<Throwable>, Observable<QueryPlan>>() {
                            @Override
                            public Observable<QueryPlan> call(ArrayList<Throwable> errors) {
                                if (errors.size() == 1) {
                                    return Observable.error(new CouchbaseException(
                                            "Error while preparing plan", errors.get(0)));
                                } else {
                                    return Observable.error(new CompositeException(
                                            "Multiple errors while preparing plan", errors));
                                }
                            }
                        });
                    }
