        return upsertResult.flatMap(new Func1<D, Observable<D>>() {
            @Override
            public Observable<D> call(final D doc) {
                return Observe
                    .call(core, bucket, doc.id(), doc.cas(), false, doc.mutationToken(), persistTo.value(), replicateTo.value(),
                        environment.observeIntervalDelay(), environment.retryStrategy())
                    .map(new Func1<Boolean, D>() {
                        @Override
                        public D call(Boolean aBoolean) {
                            return doc;
                        }
                    })
                    .onErrorResumeNext(new Func1<Throwable, Observable<? extends D>>() {
                        @Override
                        public Observable<? extends D> call(Throwable throwable) {
                            return Observable.error(new DurabilityException(
                                "Durability requirement failed: " + throwable.getMessage(),
                                throwable));
                        }
                    })
                    .timeout(timeout, timeUnit, environment.scheduler());
            }
        });
