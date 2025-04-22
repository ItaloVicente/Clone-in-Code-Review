
                final Observable<JsonObject> profileInfo = response.profileInfo().map(new Func1<ByteBuf, JsonObject>() {
                    @Override
                    public JsonObject call(ByteBuf byteBuf) {
                        try {
                            return JSON_OBJECT_TRANSCODER.byteBufToJsonObject(byteBuf);
                        } catch (Exception e) {
                            throw new TranscodingException("Could not decode profile Info.", e);
                        } finally {
                            byteBuf.release();
                        }
                    }
                });

