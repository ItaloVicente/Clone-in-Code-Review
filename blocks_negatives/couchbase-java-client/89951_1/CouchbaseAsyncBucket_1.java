                    switch(response.status()) {
                        case NOT_EXISTS:
                            return false;
                        case TEMPORARY_FAILURE:
                        case SERVER_BUSY:
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
