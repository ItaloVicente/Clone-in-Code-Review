                .<GenericQueryResponse>send(prepareRequest)
                .flatMap(new Func1<GenericQueryResponse, Observable<QueryPlan>>() {
                    @Override
                    public Observable<QueryPlan> call(GenericQueryResponse r) {
                        if (r.status().isSuccess()) {
                            r.info().subscribe(Buffers.BYTE_BUF_RELEASER);
                            r.errors().subscribe(Buffers.BYTE_BUF_RELEASER);
                            return r.rows().map(new Func1<ByteBuf, QueryPlan>() {
                                @Override
                                public QueryPlan call(ByteBuf byteBuf) {
                                    try {
                                        JsonObject value = JSON_OBJECT_TRANSCODER.byteBufToJsonObject(byteBuf);
                                        return new QueryPlan(value);
                                    } catch (Exception e) {
                                        throw new TranscodingException("Could not decode N1QL Query Plan.", e);
                                    } finally {
                                        byteBuf.release();
                                    }
