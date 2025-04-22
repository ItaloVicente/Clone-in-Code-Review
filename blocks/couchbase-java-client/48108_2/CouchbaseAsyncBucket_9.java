
                switch (response.status()) {
                    case TOO_BIG:
                        throw new RequestTooBigException();
                    case EXISTS:
                        throw new DocumentAlreadyExistsException();
                    case TEMPORARY_FAILURE:
                    case SERVER_BUSY:
                        throw new TemporaryFailureException();
                    case OUT_OF_MEMORY:
                        throw new CouchbaseOutOfMemoryException();
                    default:
                        throw new CouchbaseException(response.status().toString());
                }
            }
        });
