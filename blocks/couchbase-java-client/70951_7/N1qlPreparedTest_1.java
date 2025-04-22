        List<JsonDocument> documents = Observable.from(keys)
                .flatMap(new Func1<String, Observable<JsonDocument>>() {
                    @Override
                    public Observable<JsonDocument> call(String key) {
                        return ctx.bucket().async().upsert(JsonDocument.create(key, jsonObject)) ;
                    }
                })
                .onErrorResumeNext(new Func1<Throwable, Observable<JsonDocument>>() {
                    @Override
                    public Observable<JsonDocument> call(Throwable throwable) {
                        if (throwable instanceof TemporaryFailureException) {
                            return Observable.empty();
                        } else {
                            return Observable.error(throwable);
                        }
                    }
                })
                .toList()
                .toBlocking()
                .single();

        count = documents.size();
        assertTrue(count >= 10000);
