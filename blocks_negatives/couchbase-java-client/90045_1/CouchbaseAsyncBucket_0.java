        return deferAndWatch(new Func1<Subscriber, Observable<RemoveResponse>>() {
            @Override
            public Observable<RemoveResponse> call(Subscriber s) {
                RemoveRequest request = new RemoveRequest(document.id(), document.cas(), bucket);
                request.subscriber(s);
                return core.send(request);
            }
        }).map(new Func1<RemoveResponse, D>() {
            @Override
            public D call(final RemoveResponse response) {
                if (response.content() != null && response.content().refCnt() > 0) {
                    response.content().release();
                }

                if (response.status().isSuccess()) {
                    return (D) transcoder.newDocument(document.id(), 0, null, response.cas(), response.mutationToken());
                }

                switch (response.status()) {
                    case NOT_EXISTS:
                        throw addDetails(new DocumentDoesNotExistException(), response);
                    case EXISTS:
                    case LOCKED:
                        throw addDetails(new CASMismatchException(), response);
                    case TEMPORARY_FAILURE:
                    case SERVER_BUSY:
                        throw addDetails(new TemporaryFailureException(), response);
                    case OUT_OF_MEMORY:
                        throw addDetails(new CouchbaseOutOfMemoryException(), response);
                    default:
                        throw addDetails(new CouchbaseException(response.status().toString()), response);
                }
            }
        });
