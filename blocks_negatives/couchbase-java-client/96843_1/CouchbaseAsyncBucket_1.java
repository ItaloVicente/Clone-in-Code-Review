                    return Observe
                        .call(core, bucket, doc.id(), doc.cas(), false, doc.mutationToken(),
                            persistTo.value(), replicateTo.value(),
                            environment.observeIntervalDelay(), environment.retryStrategy(), parent)
                        .map(new Func1<Boolean, JsonLongDocument>() {
                            @Override
                            public JsonLongDocument call(Boolean aBoolean) {
                                return doc;
                            }
                        })
                        .onErrorResumeNext(new Func1<Throwable, Observable<? extends JsonLongDocument>>() {
                            @Override
                            public Observable<? extends JsonLongDocument> call(Throwable throwable) {
                                return Observable.error(new DurabilityException(
                                    "Durability requirement failed: " + throwable.getMessage(),
                                    throwable));
                            }
                        })
                        .timeout(timeout, timeUnit, environment.scheduler());
