                .flatMap(new Func1<Boolean, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> call(Boolean aBoolean) {
                        return configProvider.shutdown();
                    }
                })
