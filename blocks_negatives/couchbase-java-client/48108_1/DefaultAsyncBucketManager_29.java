            })
            .map(new Func1<GetDesignDocumentResponse, DesignDocument>() {
                @Override
                public DesignDocument call(GetDesignDocumentResponse response) {
                    JsonObject converted;
                    try {
                        converted = CouchbaseAsyncBucket.JSON_OBJECT_TRANSCODER.stringToJsonObject(
                            response.content().toString(CharsetUtil.UTF_8));
                    } catch (Exception e) {
                        throw new TranscodingException("Could not decode design document.", e);
                    } finally {
                        if (response.content() != null && response.content().refCnt() > 0) {
                            response.content().release();
                        }
