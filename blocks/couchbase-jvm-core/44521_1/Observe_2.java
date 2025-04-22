                .repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {
                    @Override
                    public Observable<?> call(Observable<? extends Void> observable) {
                        return observable.zipWith(
                            Observable.range(1, Integer.MAX_VALUE),
                            new Func2<Void, Integer, Integer>() {
                                @Override
                                public Integer call(Void aVoid, Integer attempt) {
                                    return attempt;
                                }
                            }
                        )
                        .flatMap(new Func1<Integer, Observable<?>>() {
                            @Override
                            public Observable<?> call(Integer attempt) {
                                return Observable.timer(delay.calculate(attempt), delay.unit());
                            }
                        });
                    }
                })
