        final Converter<?, ?> converter = converters.get(JsonDocument.class);
        return core.<ViewQueryResponse>send(request)
            .flatMap(new Func1<ViewQueryResponse, Observable<ViewResult>>() {
                @Override
                public Observable<ViewResult> call(final ViewQueryResponse response) {
                    return response.info().map(new Func1<ByteBuf, JsonObject>() {
                        @Override
                        public JsonObject call(ByteBuf byteBuf) {
                            return (JsonObject) converter.decode(byteBuf);
                        }
                    }).map(new Func1<JsonObject, ViewResult>() {
                        @Override
                        public ViewResult call(JsonObject jsonInfo) {
                            JsonObject error = null;
                            JsonObject debug = null;
                            int totalRows = 0;
                            boolean success = response.status().isSuccess();
                            if (success) {
                                debug = jsonInfo.getObject("debug_info");
                                totalRows = jsonInfo.getInt("total_rows");
                            } else {
                                error = jsonInfo;
                            }

                            Observable<ViewRow> rows = response.rows().map(new Func1<ByteBuf, ViewRow>() {
                                @Override
                                public ViewRow call(final ByteBuf byteBuf) {
                                    JsonObject doc = (JsonObject) converter.decode(byteBuf);
                                    String id = doc.getString("id");
                                    return new DefaultViewRow(CouchbaseBucket.this, id, doc.get("key"), doc.get("value"));
                                }
                            });
                            return new DefaultViewResult(rows, totalRows, success, error, debug);
                        }
                    });
                }
            });
