                                                    .flatMap(new Func1<List<JsonObject>, Observable<Boolean>>() {
                                                        @Override
                                                        public Observable<Boolean> call(List<JsonObject> errors) {
                                                            if (ignoreIfNotExist && errors.size() == 1
                                                                    && errors.get(0).getString("msg").contains("not found")) {
                                                                return Observable.just(false);
                                                            } else {
                                                                return Observable.error(new CouchbaseException(errorPrefix + errors));
                                                            }
