                        public JsonObject call(ByteBuf byteBuf) {
                            try {
                                return JSON_OBJECT_TRANSCODER.byteBufToJsonObject(byteBuf);
                            } catch (Exception e) {
                                throw new TranscodingException("Could not decode View Info.", e);
                            } finally {
                                byteBuf.release();
