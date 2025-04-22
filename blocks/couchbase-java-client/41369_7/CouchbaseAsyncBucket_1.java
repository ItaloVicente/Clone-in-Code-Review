                .flatMap(new Func1<ViewQueryResponse, Observable<AsyncViewResult>>() {
                    @Override
                    public Observable<AsyncViewResult> call(final ViewQueryResponse response) {
                        return response.info().map(new Func1<ByteBuf, JsonObject>() {
                            @Override
                            public JsonObject call(ByteBuf byteBuf) {
                                if (byteBuf == null || byteBuf.readableBytes() == 0) {
                                    return JsonObject.empty();
                                }
                                try {
                                    return JSON_TRANSCODER.byteBufToJsonObject(byteBuf);
                                } catch (Exception e) {
                                    throw new TranscodingException("Could not decode View Info.", e);
