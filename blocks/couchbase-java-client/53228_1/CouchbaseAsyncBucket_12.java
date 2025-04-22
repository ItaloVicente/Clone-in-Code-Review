        return Observable.defer(new Func0<Observable<GenericQueryResponse>>() {
            @Override
            public Observable<GenericQueryResponse> call() {
                return core.send(GenericQueryRequest.jsonQuery(query, bucket, password));
            }
        }).flatMap(new Func1<GenericQueryResponse, Observable<AsyncQueryResult>>() {
            @Override
            public Observable<AsyncQueryResult> call(final GenericQueryResponse response) {
                final Observable<AsyncQueryRow> rows = response.rows().map(new Func1<ByteBuf, AsyncQueryRow>() {
                    @Override
                    public AsyncQueryRow call(ByteBuf byteBuf) {
                        try {
                            JsonObject value = JSON_OBJECT_TRANSCODER.byteBufToJsonObject(byteBuf);
                            return new DefaultAsyncQueryRow(value);
                        } catch (Exception e) {
                            throw new TranscodingException("Could not decode N1QL Query Info.", e);
                        } finally {
                            byteBuf.release();
                        }
                    }
                });
                final Observable<Object> signature = response.signature().map(new Func1<ByteBuf, Object>() {
                    @Override
                    public Object call(ByteBuf byteBuf) {
                        try {
                            return JSON_OBJECT_TRANSCODER.byteBufJsonValueToObject(byteBuf);
                        } catch (Exception e) {
                            throw new TranscodingException("Could not decode N1QL Query Signature", e);
                        } finally {
                            byteBuf.release();
                        }
                    }
                });
                final Observable<JsonObject> info = response.info().map(new Func1<ByteBuf, JsonObject>() {
                    @Override
                    public JsonObject call(ByteBuf byteBuf) {
                        try {
                            return JSON_OBJECT_TRANSCODER.byteBufToJsonObject(byteBuf);
                        } catch (Exception e) {
                            throw new TranscodingException("Could not decode N1QL Query Info.", e);
                        } finally {
                            byteBuf.release();
                        }
                    }
                });
                final Observable<Boolean> finalSuccess = response.queryStatus().map(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return "success".equalsIgnoreCase(s) || "completed".equalsIgnoreCase(s);
                    }
                });
                final Observable<JsonObject> errors = response.errors().map(new Func1<ByteBuf, JsonObject>() {
                    @Override
                    public JsonObject call(ByteBuf byteBuf) {
                        try {
                            return JSON_OBJECT_TRANSCODER.byteBufToJsonObject(byteBuf);
                        } catch (Exception e) {
                            throw new TranscodingException("Could not decode View Info.", e);
                        } finally {
                            byteBuf.release();
                        }
                    }
                });
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
        final SimpleQuery query = Query.simple(prepared);

        return Observable.defer(new Func0<Observable<GenericQueryResponse>>() {
            @Override
            public Observable<GenericQueryResponse> call() {
                return core.send(GenericQueryRequest.jsonQuery(query.n1ql().toString(), bucket, password));
            }
        }).flatMap(new Func1<GenericQueryResponse, Observable<QueryPlan>>() {
            @Override
            public Observable<QueryPlan> call(GenericQueryResponse r) {
                if (r.status().isSuccess()) {
                    r.info().subscribe(Buffers.BYTE_BUF_RELEASER);
                    r.signature().subscribe(Buffers.BYTE_BUF_RELEASER);
                    r.errors().subscribe(Buffers.BYTE_BUF_RELEASER);
                    return r.rows().map(new Func1<ByteBuf, QueryPlan>() {
