
                    switch(response.status()) {
                        case NOT_EXISTS:
                            return false;
                        case TEMPORARY_FAILURE:
                            throw new TemporaryFailureException();
                        case OUT_OF_MEMORY:
                            throw new CouchbaseOutOfMemoryException();
                        default:
                            throw new CouchbaseException(response.status().toString());
                    }
