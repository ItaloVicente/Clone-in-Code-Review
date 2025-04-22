                    final Observable<JsonObject> signature = response.signature().map(new Func1<ByteBuf, JsonObject>() {
                        @Override
                        public JsonObject call(ByteBuf byteBuf) {
                            try {
                                return JSON_OBJECT_TRANSCODER.byteBufToJsonObject(byteBuf);
                            } catch (Exception e) {
                                throw new TranscodingException("Could not decode N1QL Query Signature", e);
                            } finally {
                                byteBuf.release();
                            }
                        }
                    });
