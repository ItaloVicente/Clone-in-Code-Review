                    if (response.status().isSuccess()) {
                        response.errors().subscribe(Buffers.BYTE_BUF_RELEASER);
                        return Observable.just((AsyncQueryResult) new DefaultAsyncQueryResult(rows, info, null,
                                response.status().isSuccess()));
                    } else {
                        return response.errors().map(new Func1<ByteBuf, JsonObject>() {
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
                        }).last().map(new Func1<JsonObject, AsyncQueryResult>() {
                            @Override
                            public AsyncQueryResult call(JsonObject error) {
                                return new DefaultAsyncQueryResult(rows, info, error, response.status().isSuccess());
