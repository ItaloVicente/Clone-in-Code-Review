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
                            }
                        }
                    }).map(new Func1<JsonObject, AsyncViewResult>() {
                        @Override
                        public AsyncViewResult call(JsonObject jsonInfo) {
                            JsonObject error = null;
                            JsonObject debug = null;
                            int totalRows = 0;
                            boolean success = response.status().isSuccess();
                            if (success) {
                                debug = jsonInfo.getObject("debug_info");
                                Integer trows = jsonInfo.getInt("total_rows");
                                if (trows != null) {
                                    totalRows = trows;
