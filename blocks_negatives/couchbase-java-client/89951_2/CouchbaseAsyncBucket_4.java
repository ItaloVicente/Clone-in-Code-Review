        return deferAndWatch(new Func1<Subscriber, Observable<GetResponse>>() {
                @Override
                public Observable<GetResponse> call(Subscriber s) {
                    GetRequest request = new GetRequest(id, bucket, false, true, expiry);
                    request.subscriber(s);
                    return core.send(request);
                }
            })
            .filter(new Func1<GetResponse, Boolean>() {
                @Override
                public Boolean call(GetResponse response) {
                    if (response.status().isSuccess()) {
                        return true;
                    }
                    ByteBuf content = response.content();
                    if (content != null && content.refCnt() > 0) {
                        content.release();
                    }

                    switch (response.status()) {
                        case NOT_EXISTS:
                            return false;
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
            })
            .map(new Func1<GetResponse, D>() {
                @Override
                public D call(final GetResponse response) {
                    Transcoder<?, Object> transcoder = (Transcoder<?, Object>) transcoders.get(target);
                    return (D) transcoder.decode(id, response.content(), response.cas(), 0, response.flags(),
                        response.status());
                }
            });
