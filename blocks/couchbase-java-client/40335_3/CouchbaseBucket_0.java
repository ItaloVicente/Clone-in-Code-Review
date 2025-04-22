                public Observable<QueryResult> call(final GenericQueryResponse response) {
                    final Observable<QueryRow> rows = response.rows().map(new Func1<ByteBuf, QueryRow>() {
                        @Override
                        public QueryRow call(ByteBuf byteBuf) {
                            JsonObject value = (JsonObject) converter.decode(byteBuf);
                            return new DefaultQueryRow(value);
                        }
                    });
                    final Observable<JsonObject> info = response.info().map(new Func1<ByteBuf, JsonObject>() {
                        @Override
                        public JsonObject call(ByteBuf byteBuf) {
                            JsonObject value = (JsonObject) converter.decode(byteBuf);
                            return value;
                        }
                    });
                    if (response.status().isSuccess()) {
                        return Observable.just((QueryResult) new DefaultQueryResult(rows, info, null,
                            response.status().isSuccess()));
                    } else {
                        return response.info().map(new Func1<ByteBuf, QueryResult>() {
                            @Override
                            public QueryResult call(ByteBuf byteBuf) {
                                JsonObject error = (JsonObject) converter.decode(byteBuf);
                                return new DefaultQueryResult(rows, info, error, response.status().isSuccess());
                            }
                        });
                    }
