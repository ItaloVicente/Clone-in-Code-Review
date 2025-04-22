            public Observable<DocumentFragment<Lookup>> call() {
                final SubMultiLookupRequest request = new SubMultiLookupRequest(
                    docId, bucketName, SubMultiLookupDocOptionsBuilder.builder().accessDeleted(accessDeleted), lookupSpecs
                );
                addRequestSpan(environment, request, "subdoc_multi_lookup");
                return applyTimeout(deferAndWatch(new Func1<Subscriber, Observable<MultiLookupResponse>>() {
                    @Override
                    public Observable<MultiLookupResponse> call(Subscriber s) {
                        request.subscriber(s);
                        return core.send(request);
                    }
                }).filter(new Func1<MultiLookupResponse, Boolean>() {
                    @Override
                    public Boolean call(MultiLookupResponse response) {
                        if (response.status().isSuccess() || response.status() == ResponseStatus.SUBDOC_MULTI_PATH_FAILURE) {
                            return true;
                        }
