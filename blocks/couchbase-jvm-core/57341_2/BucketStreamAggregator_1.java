    private Observable<DCPConnection> open() {
        if (connection == null) {
            return core.<OpenConnectionResponse>send(new OpenConnectionRequest(name, bucket))
                    .flatMap(new Func1<OpenConnectionResponse, Observable<DCPConnection>>() {
                        @Override
                        public Observable<DCPConnection> call(final OpenConnectionResponse response) {
                            connection = response.connection();
                            return Observable.just(connection);
                        }
                    });
        }
        return Observable.just(connection);
    }

