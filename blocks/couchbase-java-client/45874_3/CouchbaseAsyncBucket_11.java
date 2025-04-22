
                switch(response.status()) {
                    case TOO_BIG:
                        return Observable.error(new RequestTooBigException());
                    case NOT_EXISTS:
                        return Observable.error(new DocumentDoesNotExistException());
                    case EXISTS:
                        return Observable.error(new CASMismatchException());
                    case TEMPORARY_FAILURE:
                        return Observable.error(new TemporaryFailureException());
                    case OUT_OF_MEMORY:
                        return Observable.error(new CouchbaseOutOfMemoryException());
                    default:
                        return Observable.error(new CouchbaseException(response.status().toString()));
