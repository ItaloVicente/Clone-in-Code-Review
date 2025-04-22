                switch (response.status()) {
                    case NOT_EXISTS:
                        throw addDetails(new DocumentDoesNotExistException(), response);
                    case TEMPORARY_FAILURE:
                    case LOCKED:
                        throw addDetails(new TemporaryLockFailureException(), response);
                    case SERVER_BUSY:
                        throw addDetails(new TemporaryFailureException(), response);
                    case OUT_OF_MEMORY:
                        throw addDetails(new CouchbaseOutOfMemoryException(), response);
                    default:
                        throw addDetails(new CouchbaseException(response.status().toString()), response);
                }
            }
        });
