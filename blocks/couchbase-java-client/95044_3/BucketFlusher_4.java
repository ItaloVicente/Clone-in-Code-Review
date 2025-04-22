        return deferAndWatch(new Func1<Subscriber, Observable<? extends Boolean>>() {
            @Override
            public Observable<? extends Boolean> call(final Subscriber subscriber) {
                return Observable
                    .from(FLUSH_MARKERS)
                    .flatMap(new Func1<String, Observable<GetResponse>>() {
                        @Override
                        public Observable<GetResponse> call(String id) {
                            GetRequest req = new GetRequest(id, bucket);
                            req.subscriber(subscriber);
                            return core.send(req);
                        }
                    })
                    .reduce(0, new Func2<Integer, GetResponse, Integer>() {
                        @Override
                        public Integer call(Integer foundDocs, GetResponse response) {
                            if (response.content() != null && response.content().refCnt() > 0) {
                                response.content().release();
                            }
                            if (response.status() == ResponseStatus.SUCCESS) {
                                foundDocs++;
                            }
                            return foundDocs;
                        }
                    })
                    .filter(new Func1<Integer, Boolean>() {
                        @Override
                        public Boolean call(Integer foundDocs) {
                            return foundDocs == 0;
                        }
                    })
                    .repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {
                        @Override
                        public Observable<?> call(Observable<? extends Void> observable) {
                            return observable.flatMap(new Func1<Void, Observable<?>>() {
                                @Override
                                public Observable<?> call(Void aVoid) {
                                    return Observable.timer(500, TimeUnit.MILLISECONDS);
                                }
                            });
                        }
                    })
                    .take(1)
                    .map(new Func1<Integer, Boolean>() {
