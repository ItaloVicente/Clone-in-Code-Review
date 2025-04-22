
        return deferAndWatch(new Func1<Subscriber, Observable<InsertResponse>>() {
            @Override
            public Observable<InsertResponse> call(Subscriber s) {
                Tuple2<ByteBuf, Integer> encoded = transcoder.encode((Document<Object>) document);
                InsertRequest request = new InsertRequest(document.id(), encoded.value1(), document.expiry(), encoded.value2(), bucket);
                request.subscriber(s);
                return core.send(request);
            }
        }).map(new Func1<InsertResponse, D>() {
            @Override
            public D call(InsertResponse response) {
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
                        throw addDetails(new DocumentAlreadyExistsException(), response);
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
