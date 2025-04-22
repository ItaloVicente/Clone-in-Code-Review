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
