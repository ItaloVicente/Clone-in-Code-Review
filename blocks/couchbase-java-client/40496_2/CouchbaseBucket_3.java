                                    JsonObject doc;
                                    try {
                                        doc = JSON_TRANSCODER.byteBufToJsonObject(byteBuf);
                                    } catch (Exception e) {
                                        throw new TranscodingException("Could not decode View Info.", e);
                                    }
