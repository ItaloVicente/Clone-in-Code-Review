                        .flatMap(new Func1<Boolean, Observable<Boolean>>() {
                            @Override
                            public Observable<Boolean> call(Boolean success) {
                                if (success) {
                                    return Observable.just(true);
                                } else {
                                    return aqr.errors().toList()
                                        .flatMap(new Func1<List<JsonObject>, Observable<Boolean>>() {
                                            @Override
                                            public Observable<Boolean> call(List<JsonObject> errors) {
                                                if (ignoreIfExist && errors.size() == 1
                                                    && errors.get(0)
                                                    .getString("msg")
                                                    .contains("already exist")) {
                                                    return Observable.just(false);
                                                } else {
                                                    return Observable.error(new CouchbaseException(
                                                        "Error creating primary index: " + errors));
                                                }
                                            }
                                        });
                                }
                            }
                        });
