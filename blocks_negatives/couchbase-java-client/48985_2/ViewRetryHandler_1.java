                public ViewQueryResponse call(ByteBuf infoBuffer) {
                    ByteBuf infoBufferCopy = infoBuffer.copy();
                    JsonObject content = null;
                    try {
                        content =
                            CouchbaseAsyncBucket.JSON_OBJECT_TRANSCODER.byteBufToJsonObject(infoBufferCopy);
                    } catch (Exception e) {
                        if (infoBuffer.refCnt() > 0) {
                            infoBuffer.release();
                        }
                        throw new CouchbaseException("Could not parse View error message", e);
                    } finally {
                        infoBufferCopy.release();
                    }

                    if (shouldRetry(responseCode, content)) {
                        if (infoBuffer.refCnt() > 0) {
                            infoBuffer.release();
                        }
