        return deferAndWatch(new Func1<Subscriber, Observable<MultiLookupResponse>>() {
            @Override
            public Observable<MultiLookupResponse> call(Subscriber s) {
                SubMultiLookupRequest request = new SubMultiLookupRequest(docId, bucketName, SubMultiLookupDocOptionsBuilder.builder().accessDeleted(accessDeleted), lookupSpecs);
                request.subscriber(s);
                return core.send(request);
            }
        }).filter(new Func1<MultiLookupResponse, Boolean>() {
