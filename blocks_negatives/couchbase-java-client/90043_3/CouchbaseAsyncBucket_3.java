                switch (response.status()) {
                    case TOO_BIG:
                        throw addDetails(new RequestTooBigException(), response);
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
