                public Observable<UpsertResponse> call(final String id) {
                    return deferAndWatch(new Func1<Subscriber, Observable<? extends UpsertResponse>>() {
                        @Override
                        public Observable<? extends UpsertResponse> call(final Subscriber subscriber) {
                            UpsertRequest request = new UpsertRequest(id, Unpooled.copiedBuffer(id, CharsetUtil.UTF_8), bucket);
                            request.subscriber(subscriber);
                            return core.send(request);
                        }
                    });
