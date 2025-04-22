
                                Observable<AsyncViewRow> rows = response.rows().map(new Func1<ByteBuf, AsyncViewRow>() {
                                    @Override
                                    public AsyncViewRow call(final ByteBuf byteBuf) {
                                        JsonObject doc;
                                        try {
                                            doc = JSON_TRANSCODER.byteBufToJsonObject(byteBuf);
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
                    }
                });
    }
