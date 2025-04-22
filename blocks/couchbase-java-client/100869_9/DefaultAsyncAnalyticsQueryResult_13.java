        return finalStatus.flatMap(new Func1<String, Observable<AsyncAnalyticsQueryRow>>() {
            @Override
            public Observable<AsyncAnalyticsQueryRow> call(String s) {
                if (s.equalsIgnoreCase("running")) {
                    return Observable.just(null);
                } else {
                    return rows;
                }
            }
        });
