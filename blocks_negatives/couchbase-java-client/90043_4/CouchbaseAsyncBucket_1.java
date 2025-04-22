
        return deferAndWatch(new Func1<Subscriber, Observable<UpsertResponse>>() {
            @Override
            public Observable<UpsertResponse> call(Subscriber s) {
                Tuple2<ByteBuf, Integer> encoded = transcoder.encode((Document<Object>) document);
                UpsertRequest request = new UpsertRequest(document.id(), encoded.value1(), document.expiry(), encoded.value2(), bucket);
                request.subscriber(s);
                return core.send(request);
            }
        }).map(new Func1<UpsertResponse, D>() {
            @Override
            public D call(UpsertResponse response) {
                if (response.content() != null && response.content().refCnt() > 0) {
                    response.content().release();
                }

                if (response.status().isSuccess()) {
                    return (D) transcoder.newDocument(document.id(), document.expiry(),
                        document.content(), response.cas(), response.mutationToken());
                }

                switch (response.status()) {
                    case TOO_BIG:
                        throw addDetails(new RequestTooBigException(), response);
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
