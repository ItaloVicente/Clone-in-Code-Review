                return applyTimeout(deferAndWatch(new Func1<Subscriber, Observable<MultiMutationResponse>>() {
                        @Override
                        public Observable<MultiMutationResponse> call(Subscriber subscriber) {
                            req.subscriber(subscriber);
                            return core.send(req);
                        }
                    })
