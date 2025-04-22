    @Override
    public Observable<PreparedPayload> prepare(Statement statement) {
        final PrepareStatement prepared;
        if (statement instanceof PrepareStatement) {
            prepared = (PrepareStatement) statement;
        } else {
            prepared = PrepareStatement.prepare(statement);
        }
        final SimpleQuery query = Query.simple(prepared);

        return Observable.defer(new Func0<Observable<GenericQueryResponse>>() {
            @Override
            public Observable<GenericQueryResponse> call() {
                return core.send(GenericQueryRequest.jsonQuery(query.n1ql().toString(), bucket, password));
            }
        }).flatMap(new Func1<GenericQueryResponse, Observable<PreparedPayload>>() {
            @Override
            public Observable<PreparedPayload> call(GenericQueryResponse r) {
                if (r.status().isSuccess()) {
                    r.info().subscribe(Buffers.BYTE_BUF_RELEASER);
                    r.signature().subscribe(Buffers.BYTE_BUF_RELEASER);
                    r.errors().subscribe(Buffers.BYTE_BUF_RELEASER);
                    return r.rows().map(new Func1<ByteBuf, PreparedPayload>() {
                        @Override
                        public PreparedPayload call(ByteBuf byteBuf) {
                            try {
                                JsonObject value = JSON_OBJECT_TRANSCODER.byteBufToJsonObject(byteBuf);
                                String serverName = value.getString("name");
                                if (prepared.preparedName() != null && !prepared.preparedName().equals(serverName)) {
                                    throw new IllegalStateException("Prepared statement name from server differs: " +
                                            serverName + ", expected " + prepared.preparedName());
                                }
                                return new PreparedPayload(prepared.originalStatement(), prepared.preparedName());
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
                    })
                    .reduce(new ArrayList<Throwable>(),
                            new Func2<ArrayList<Throwable>, Exception, ArrayList<Throwable>>() {
                                @Override
                                public ArrayList<Throwable> call(ArrayList<Throwable> throwables,
                                        Exception error) {
                                    throwables.add(error);
                                    return throwables;
                                }
                            })
                    .flatMap(new Func1<ArrayList<Throwable>, Observable<PreparedPayload>>() {
                        @Override
                        public Observable<PreparedPayload> call(ArrayList<Throwable> errors) {
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
            }
        });
    }

