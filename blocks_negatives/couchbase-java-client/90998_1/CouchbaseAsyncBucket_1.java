        return deferAndWatch(new Func1<Subscriber, Observable<PrependResponse>>() {
            @Override
            public Observable<PrependResponse> call(Subscriber s) {
                Tuple2<ByteBuf, Integer> encoded = transcoder.encode((Document<Object>) document);
                PrependRequest request = new PrependRequest(document.id(), document.cas(), encoded.value1(), bucket);
                request.subscriber(s);
                return core.send(request);
            }
        }).map(new Func1<PrependResponse, D>() {
            @Override
            public D call(final PrependResponse response) {
                if (response.content() != null && response.content().refCnt() > 0) {
                    response.content().release();
                }

                if (response.status().isSuccess()) {
                    return (D) transcoder.newDocument(document.id(), 0, null, response.cas(), response.mutationToken());
                }

                switch (response.status()) {
                    case TOO_BIG:
                        throw addDetails(new RequestTooBigException(), response);
                    case NOT_STORED:
                        throw addDetails(new DocumentDoesNotExistException(), response);
                    case TEMPORARY_FAILURE:
                    case SERVER_BUSY:
                    case LOCKED:
                        throw addDetails(new TemporaryFailureException(), response);
                    case OUT_OF_MEMORY:
                        throw addDetails(new CouchbaseOutOfMemoryException(), response);
                    case EXISTS:
                        throw addDetails(new CASMismatchException(), response);
                    default:
                        throw addDetails(new CouchbaseException(response.status().toString()), response);
                }
            }
        });
