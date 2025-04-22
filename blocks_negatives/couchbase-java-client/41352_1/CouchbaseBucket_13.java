    public Observable<QueryResult> query(final String query) {
        GenericQueryRequest request = new GenericQueryRequest(query, bucket, password);
        return core
            .<GenericQueryResponse>send(request)
            .flatMap(new Func1<GenericQueryResponse, Observable<QueryResult>>() {
                @Override
                public Observable<QueryResult> call(final GenericQueryResponse response) {
                    final Observable<QueryRow> rows = response.rows().map(new Func1<ByteBuf, QueryRow>() {
                        @Override
                        public QueryRow call(ByteBuf byteBuf) {
                            try {
                                JsonObject value = JSON_TRANSCODER.byteBufToJsonObject(byteBuf);
                                return new DefaultQueryRow(value);
                            } catch (Exception e) {
                                throw new TranscodingException("Could not decode View Info.", e);
                            }
                        }
                    });
                    final Observable<JsonObject> info = response.info().map(new Func1<ByteBuf, JsonObject>() {
                        @Override
                        public JsonObject call(ByteBuf byteBuf) {
                            try {
                                return JSON_TRANSCODER.byteBufToJsonObject(byteBuf);
                            } catch (Exception e) {
                                throw new TranscodingException("Could not decode View Info.", e);
                            }
                        }
                    });
                    if (response.status().isSuccess()) {
                        return Observable.just((QueryResult) new DefaultQueryResult(rows, info, null,
                            response.status().isSuccess()));
                    } else {
                        return response.info().map(new Func1<ByteBuf, QueryResult>() {
                            @Override
                            public QueryResult call(ByteBuf byteBuf) {
                                try {
                                    JsonObject error = JSON_TRANSCODER.byteBufToJsonObject(byteBuf);
                                    return new DefaultQueryResult(rows, info, error, response.status().isSuccess());
                                } catch (Exception e) {
                                    throw new TranscodingException("Could not decode View Info.", e);
                                }

                            }
                        });
                    }
                }
            });
