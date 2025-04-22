
                    if (response.status().isSuccess()) {
                        return Observable.just((D) transcoder.newDocument(document.id(), document.expiry(),
                            document.content(), response.cas()));
                    }

                    switch(response.status()) {
                        case TOO_BIG:
                            return Observable.error(new RequestTooBigException());
                        case EXISTS:
                            return Observable.error(new CASMismatchException());
                        case TEMPORARY_FAILURE:
                            return Observable.error(new TemporaryFailureException());
                        case OUT_OF_MEMORY:
                            return Observable.error(new CouchbaseOutOfMemoryException());
                        default:
                            return Observable.error(new CouchbaseException(response.status().toString()));
