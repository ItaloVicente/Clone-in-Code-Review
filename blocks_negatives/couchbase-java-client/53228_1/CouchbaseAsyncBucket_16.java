                    switch(response.status()) {
                        case NOT_EXISTS:
                            throw new DocumentDoesNotExistException();
                        case TEMPORARY_FAILURE:
                            throw new TemporaryLockFailureException();
                        case SERVER_BUSY:
                            throw new TemporaryFailureException();
                        case OUT_OF_MEMORY:
                            throw new CouchbaseOutOfMemoryException();
                        default:
                            throw new CouchbaseException(response.status().toString());
                    }
