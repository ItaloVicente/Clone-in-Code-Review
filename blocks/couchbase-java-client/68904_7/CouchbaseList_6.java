                        }).onErrorResumeNext(new Func1<Throwable, Observable<? extends E>>() {
                            @Override
                            public Observable<? extends E> call(Throwable throwable) {
                                if (throwable instanceof PathInvalidException) {
                                    return Observable.error(new IndexOutOfBoundsException());
                                }
                                return Observable.error(throwable);
                            }
