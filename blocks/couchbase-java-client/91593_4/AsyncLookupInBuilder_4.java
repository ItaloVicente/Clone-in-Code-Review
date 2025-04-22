                addRequestSpan(environment, request, "subdoc_exists");
                return applyTimeout(deferAndWatch(new Func1<Subscriber, Observable<SimpleSubdocResponse>>() {
                    @Override
                    public Observable<SimpleSubdocResponse> call(Subscriber s) {
                        request.subscriber(s);
                        return core.send(request);
                    }
                }).map(new Func1<SimpleSubdocResponse, DocumentFragment<Lookup>>() {
                    @Override
                    public DocumentFragment<Lookup> call(SimpleSubdocResponse response) {
                        try {
                            if (response.content() != null && response.content().refCnt() > 0) {
                                response.content().release();
                            }
