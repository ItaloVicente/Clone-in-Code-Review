        GenericQueryRequest request = GenericQueryRequest.jsonQuery(query, bucket, password);
        return core
            .<GenericQueryResponse>send(request)
            .flatMap(new Func1<GenericQueryResponse, Observable<AsyncQueryResult>>() {
                @Override
                public Observable<AsyncQueryResult> call(final GenericQueryResponse response) {
                    final Observable<AsyncQueryRow> rows = response.rows().map(new Func1<ByteBuf, AsyncQueryRow>() {
                        @Override
                        public AsyncQueryRow call(ByteBuf byteBuf) {
                            try {
                                JsonObject value = JSON_OBJECT_TRANSCODER.byteBufToJsonObject(byteBuf);
                                return new DefaultAsyncQueryRow(value);
                            } catch (Exception e) {
                                throw new TranscodingException("Could not decode N1QL Query Info.", e);
                            } finally {
                                byteBuf.release();
                            }
                        }
                    });
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
