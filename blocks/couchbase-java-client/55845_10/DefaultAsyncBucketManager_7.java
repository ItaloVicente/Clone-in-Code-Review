            N1qlQuery.simple(listIndexes, N1qlParams.build().consistency(ScanConsistency.REQUEST_PLUS)))
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
