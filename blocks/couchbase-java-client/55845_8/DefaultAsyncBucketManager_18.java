                .flatMap(new Func1<AsyncN1qlQueryResult, Observable<AsyncN1qlQueryRow>>() {
                    @Override
                    public Observable<AsyncN1qlQueryRow> call(final AsyncN1qlQueryResult aqr) {
                        return aqr.finalSuccess()
                                .flatMap(new Func1<Boolean, Observable<AsyncN1qlQueryRow>>() {
                                    @Override
                                    public Observable<AsyncN1qlQueryRow> call(Boolean success) {
                                        if (success) {
                                            return aqr.rows();
                                        } else {
                                            return aqr.errors().toList().flatMap(errorHandler);
                                        }
                                    }
                                });
                    }
                }).map(ROW_VALUE_TO_INDEXINFO);
