                    }).onErrorResumeNext(new Func1<Throwable, Observable<? extends D>>() {
                        @Override
                        public Observable<? extends D> call(Throwable throwable) {
                            return Observable.error(new DurabilityException("Durability requirement failed: " + throwable.getMessage(),
                                throwable));
                        }
