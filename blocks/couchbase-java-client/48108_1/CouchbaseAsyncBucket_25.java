                switch (response.status()) {
                    case TEMPORARY_FAILURE:
                    case SERVER_BUSY:
                        throw new TemporaryFailureException();
                    case OUT_OF_MEMORY:
                        throw new CouchbaseOutOfMemoryException();
                    default:
                        throw new CouchbaseException(response.status().toString());
