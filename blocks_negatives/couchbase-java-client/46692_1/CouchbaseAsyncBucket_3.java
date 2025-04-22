                    .call(core, bucket, doc.id(), doc.cas(), true, persistTo.value(), replicateTo.value(),
                        environment.observeIntervalDelay(), environment.retryStrategy())
                    .map(new Func1<Boolean, D>() {
                        @Override
                        public D call(Boolean aBoolean) {
                            return doc;
                        }
                    }).onErrorResumeNext(new Func1<Throwable, Observable<? extends D>>() {
                        @Override
                        public Observable<? extends D> call(Throwable throwable) {
                            return Observable.error(new DurabilityException("Durability requirement failed: " + throwable.getMessage(),
                                throwable));
                        }
                    });
