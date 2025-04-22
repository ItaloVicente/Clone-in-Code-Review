        return finalStatus.flatMap(new Func1<String, Observable<String>>() {
            @Override
            public Observable<String> call(String s) {
                if (s.equalsIgnoreCase("running")) {
                    finalStatus = handle.status();
                }
                return finalStatus;
            }
        });
    }

    @Override
    public Observable<String> status(final long timeout, final TimeUnit timeunit) {
        return finalStatus.flatMap(new Func1<String, Observable<String>>() {
            @Override
            public Observable<String> call(String s) {
                if (s.equalsIgnoreCase("running")) {
                    finalStatus = handle.status(timeout, timeunit);
                }
                return finalStatus;
            }
        });
