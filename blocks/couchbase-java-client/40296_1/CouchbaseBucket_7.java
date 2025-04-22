                        @Override
                        public D call(Boolean aBoolean) {
                            return doc;
                        }
                    }).onErrorFlatMap(new Func1<OnErrorThrowable, Observable<? extends D>>() {
                        @Override
                        public Observable<? extends D> call(OnErrorThrowable onErrorThrowable) {
                            return Observable.error(new DurabilityException("Durability constraint failed.", onErrorThrowable));
                        }
                    });
