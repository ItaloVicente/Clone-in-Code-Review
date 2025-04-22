                    return response.info().map(new Func1<ByteBuf, JsonObject>() {
                        @Override
                        public JsonObject call(ByteBuf byteBuf) {
                            if (byteBuf == null || byteBuf.readableBytes() == 0) {
                                return JsonObject.empty();
                            }
                            try {
                                return JSON_OBJECT_TRANSCODER.byteBufToJsonObject(byteBuf);
                            } catch (Exception e) {
                                throw new TranscodingException("Could not decode View Info.", e);
                            }
                        }
                    }).map(new Func1<JsonObject, AsyncViewResult>() {
                        @Override
                        public AsyncViewResult call(JsonObject jsonInfo) {
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
                            } else if (response.status() == ResponseStatus.NOT_EXISTS) {
                                throw new ViewDoesNotExistException("View " + query.getDesign() + "/"
                                    + query.getView() + " does not exist.");
                            } else {
                                error = jsonInfo;
                            }

                            Observable<AsyncViewRow> rows = response.rows().map(new Func1<ByteBuf, AsyncViewRow>() {
                                @Override
                                public AsyncViewRow call(final ByteBuf byteBuf) {
                                    JsonObject doc;
                                    try {
                                        doc = JSON_OBJECT_TRANSCODER.byteBufToJsonObject(byteBuf);
                                    } catch (Exception e) {
                                        throw new TranscodingException("Could not decode View Info.", e);
                                    }
                                    String id = doc.getString("id");
                                    return new DefaultAsyncViewRow(CouchbaseAsyncBucket.this, id, doc.get("key"), doc.get("value"));
                                }
                            });
                            return new DefaultAsyncViewResult(rows, totalRows, success, error, debug);
                        }
                    });
