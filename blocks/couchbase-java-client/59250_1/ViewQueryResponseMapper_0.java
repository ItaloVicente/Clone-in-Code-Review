                    .rows()
                    .map(new ByteBufToJsonObject()).compose(new Observable.Transformer<JsonObject, AsyncViewRow>() {
                        @Override
                        public Observable<AsyncViewRow> call(Observable<JsonObject> jsonObjectObservable) {
                            DocumentEmitMode mode = query.getDocumentEmitMode();
                            if (mode == DocumentEmitMode.CONCAT) {
                                return jsonObjectObservable.concatMap(buildAsyncViewRow());
                            } else if (mode == DocumentEmitMode.CONCAT_EAGER) {
                                return jsonObjectObservable.concatMapEager(buildAsyncViewRow());
                            } else {
                                return jsonObjectObservable.flatMap(buildAsyncViewRow());
                            }
                        }
