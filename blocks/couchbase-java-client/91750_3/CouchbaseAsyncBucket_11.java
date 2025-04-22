        final Span parent;
        if (environment.tracingEnabled()) {
            Scope scope = environment.tracer()
                .buildSpan("counter_with_durability")
                .startActive(false);
            parent = scope.span();
            scope.close();
        } else {
            parent = null;
        }

        return counter(id, delta, initial, expiry, parent, timeout, timeUnit)
            .flatMap(new Func1<JsonLongDocument, Observable<JsonLongDocument>>() {
                @Override
                public Observable<JsonLongDocument> call(final JsonLongDocument doc) {
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
                }
            })
            .doOnTerminate(new Action0() {
                @Override
                public void call() {
                    if (parent != null) {
                        environment.tracer().scopeManager()
                            .activate(parent, true)
                            .close();
                    }
                }
            });
