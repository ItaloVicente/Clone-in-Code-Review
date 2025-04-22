
                if (response.status().isSuccess()) {
                    return true;
                }

                switch(response.status()) {
                    case TEMPORARY_FAILURE:
                        throw new TemporaryFailureException();
                    case OUT_OF_MEMORY:
                        throw new CouchbaseOutOfMemoryException();
                    default:
                        throw new CouchbaseException(response.status().toString());
                }
