            }
        })
        .repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {
            @Override
            public Observable<?> call(Observable<? extends Void> observable) {
                return observable.flatMap(new Func1<Void, Observable<?>>() {
                    @Override
                    public Observable<?> call(Void aVoid) {
                        if (HttpRefresher.this.registrations().containsKey(name)) {
                            LOGGER.debug("Resubscribing config stream for bucket {}, still registered.", name);
                            return Observable.just(true);
                        } else {
                            LOGGER.debug("Not resubscribing config stream for bucket {}, not registered.", name);
                            return Observable.empty();
                        }
                    }
                });
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String rawConfig) {
                pushConfig(rawConfig);
