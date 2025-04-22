                addRequestSpan(environment, request, "subdoc_get");
                return deferAndWatch(new Func1<Subscriber, Observable<SimpleSubdocResponse>>() {
                    @Override
                    public Observable<SimpleSubdocResponse> call(Subscriber s) {
                        request.subscriber(s);
                        return core.send(request);
