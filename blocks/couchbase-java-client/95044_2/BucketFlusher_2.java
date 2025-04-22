        return deferAndWatch(new Func1<Subscriber, Observable<? extends List<String>>>() {
            @Override
            public Observable<? extends List<String>> call(final Subscriber subscriber) {
                return Observable
                    .from(FLUSH_MARKERS)
                    .flatMap(new Func1<String, Observable<UpsertResponse>>() {
                        @Override
                        public Observable<UpsertResponse> call(String id) {
                            UpsertRequest req = new UpsertRequest(id, Unpooled.copiedBuffer(id, CharsetUtil.UTF_8), bucket);
                            req.subscriber(subscriber);
                            return core.send(req);
                        }
                    })
                    .doOnNext(new Action1<UpsertResponse>() {
                        @Override
                        public void call(UpsertResponse response) {
                            if (response.content() != null && response.content().refCnt() > 0) {
                                response.content().release();
                            }
                        }
                    })
                    .last()
                    .map(new Func1<UpsertResponse, List<String>>() {
                        @Override
                        public List<String> call(UpsertResponse response) {
                            return FLUSH_MARKERS;
                        }
                    });
            }
        });
