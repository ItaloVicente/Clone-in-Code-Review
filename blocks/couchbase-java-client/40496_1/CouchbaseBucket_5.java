                                try {
                                    JsonObject error = JSON_TRANSCODER.byteBufToJsonObject(byteBuf);
                                    return new DefaultQueryResult(rows, info, error, response.status().isSuccess());
                                } catch (Exception e) {
                                    throw new TranscodingException("Could not decode View Info.", e);
                                }

