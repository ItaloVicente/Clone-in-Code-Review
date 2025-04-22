                        return queryExecutor.execute(N1qlQuery.simple(buildStatement))
                                .flatMap(new Func1<AsyncN1qlQueryResult, Observable<List<String>>>() {
                                    @Override
                                    public Observable<List<String>> call(final AsyncN1qlQueryResult aqr) {
                                        return aqr.finalSuccess()
                                                .flatMap(new Func1<Boolean, Observable<List<String>>>() {
                                                    @Override
                                                    public Observable<List<String>> call(
                                                            Boolean success) {
                                                        if (success) {
                                                            return Observable.just(pendingIndexes);
                                                        } else {
                                                            return aqr.errors().toList().flatMap(errorHandler);
                                                        }
                                                    }
                                                });
                                    }
                                });
                    }
                });
