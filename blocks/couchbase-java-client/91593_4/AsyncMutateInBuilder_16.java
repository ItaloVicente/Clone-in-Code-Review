        return Observable.defer(new Func0<Observable<DocumentFragment<Mutation>>>() {
            @Override
            public Observable<DocumentFragment<Mutation>> call() {
                final SubCounterRequest request = new SubCounterRequest(docId, spec.path(), delta, bucketName, expiry, cas);
                request.createIntermediaryPath(spec.createPath());
                request.xattr(spec.xattr());
                request.upsertDocument(upsertDocument);
                request.insertDocument(insertDocument);
                addRequestSpan(environment, request, "subdoc_counter");
                return applyTimeout(deferAndWatch(new Func1<Subscriber, Observable<SimpleSubdocResponse>>() {
