  public Observable<ViewResult> query(final ViewQuery query) {
    final ViewQueryRequest request = new ViewQueryRequest(query.getDesign(), query.getView(), query.isDevelopment(),
        query.toString(), bucket, password);
        return core.<ViewQueryResponse>send(request)
            .flatMap(new Func1<ViewQueryResponse, Observable<ViewResult>>() {
                @Override
                public Observable<ViewResult> call(final ViewQueryResponse response) {
                    return response.info().map(new Func1<ByteBuf, JsonObject>() {
                        @Override
                        public JsonObject call(ByteBuf byteBuf) {
                            if (byteBuf == null || byteBuf.readableBytes() == 0) {
                                return JsonObject.empty();
                            }
                            try {
                                return JSON_TRANSCODER.byteBufToJsonObject(byteBuf);
                            } catch (Exception e) {
                                throw new TranscodingException("Could not decode View Info.", e);
                            }
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
                                Integer trows = jsonInfo.getInt("total_rows");
                                if (trows != null) {
                                    totalRows = trows;
                                }
                            } else {
                                error = jsonInfo;
                            }

                            Observable<ViewRow> rows = response.rows().map(new Func1<ByteBuf, ViewRow>() {
                                @Override
                                public ViewRow call(final ByteBuf byteBuf) {
                                    JsonObject doc;
                                    try {
                                        doc = JSON_TRANSCODER.byteBufToJsonObject(byteBuf);
                                    } catch (Exception e) {
                                        throw new TranscodingException("Could not decode View Info.", e);
                                    }
                                    String id = doc.getString("id");
                                    return new DefaultViewRow(CouchbaseBucket.this, id, doc.get("key"), doc.get("value"));
                                }
                            });
                            return new DefaultViewResult(rows, totalRows, success, error, debug);
                        }
                    });
                }
            });
  }
