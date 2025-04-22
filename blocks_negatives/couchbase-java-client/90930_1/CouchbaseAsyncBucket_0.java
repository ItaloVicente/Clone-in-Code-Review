    public Observable<JsonLongDocument> counter(final String id, final long delta, final long initial, final int expiry) {
        return deferAndWatch(new Func1<Subscriber, Observable<CounterResponse>>() {
            @Override
            public Observable<CounterResponse> call(Subscriber s) {
                CounterRequest request = new CounterRequest(id, initial, delta, expiry, bucket);
                request.subscriber(s);
                return core.send(request);
            }
        }).map(new Func1<CounterResponse, JsonLongDocument>() {
            @Override
            public JsonLongDocument call(CounterResponse response) {
                if (response.content() != null && response.content().refCnt() > 0) {
                    response.content().release();
                }

                if (response.status().isSuccess()) {
                    int returnedExpiry = expiry == COUNTER_NOT_EXISTS_EXPIRY ? 0 : expiry;
                    return JsonLongDocument.create(id, returnedExpiry, response.value(),
                        response.cas(), response.mutationToken());
                }

                switch (response.status()) {
                    case NOT_EXISTS:
                        throw addDetails(new DocumentDoesNotExistException(), response);
                    case TEMPORARY_FAILURE:
                    case SERVER_BUSY:
                    case LOCKED:
                        throw addDetails(new TemporaryFailureException(), response);
                    case OUT_OF_MEMORY:
                        throw addDetails(new CouchbaseOutOfMemoryException(), response);
                    default:
                        throw addDetails(new CouchbaseException(response.status().toString()), response);
                }
            }
        });
