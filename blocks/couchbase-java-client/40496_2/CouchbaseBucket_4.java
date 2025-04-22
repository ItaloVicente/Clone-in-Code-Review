                            try {
                                JsonObject value = JSON_TRANSCODER.byteBufToJsonObject(byteBuf);
                                return new DefaultQueryRow(value);
                            } catch (Exception e) {
                                throw new TranscodingException("Could not decode View Info.", e);
                            }
