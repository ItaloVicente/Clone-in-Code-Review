                .flatMap(new Func1<AsyncN1qlQueryResult, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> call(final AsyncN1qlQueryResult aqr) {
                        return aqr.finalSuccess()
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
                                                        if (errors.size() == 1 && errors.get(0).getString("msg").contains("not found")) {
                                                            if (ignoreIfNotExist) {
                                                                return Observable.just(false);
                                                            } else {
                                                                return Observable.error(new IndexDoesNotExistException(errorPrefix));
                                                            }
                                                        } else {
                                                            return Observable.error(new CouchbaseException(errorPrefix + ": " + errors));
                                                        }
                                                    }
                                                });
                                        }
                                    }
                                });
                    }
                });
