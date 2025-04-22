        return finalStatus.flatMap(new Func1<String, Observable<AsyncAnalyticsQueryRow>>() {
            @Override
            public Observable<AsyncAnalyticsQueryRow> call(String s) {
                if (s.equalsIgnoreCase("running")) {
                    return Observable.just(null);
                } else {
                    if (handle != null && rows == null) {
                        rows = handle.rows();
                    }
                    return rows;
                }
            }
        });
    }

    @Override
    public Observable<AsyncAnalyticsQueryRow> rows(final long timeout, final TimeUnit timeunit) {
        return finalStatus.flatMap(new Func1<String, Observable<AsyncAnalyticsQueryRow>>() {
            @Override
            public Observable<AsyncAnalyticsQueryRow> call(String s) {
                if (s.equalsIgnoreCase("running")) {
                    return Observable.just(null);
                } else {
                    if (handle != null && rows == null) {
                        rows = handle.rows(timeout, timeunit);
                    }
                    return rows;
                }
            }
        });
