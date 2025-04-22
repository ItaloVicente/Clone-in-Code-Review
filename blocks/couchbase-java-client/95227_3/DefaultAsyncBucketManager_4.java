                return deferAndWatch(new Func1<Subscriber, Observable<? extends GetDesignDocumentsResponse>>() {
                    @Override
                    public Observable<? extends GetDesignDocumentsResponse> call(final Subscriber subscriber) {
                        GetDesignDocumentsRequest request = new GetDesignDocumentsRequest(bucket, username, password);
                        request.subscriber(subscriber);
                        return core.send(request);
                    }
                });
