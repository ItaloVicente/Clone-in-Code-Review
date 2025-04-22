
                    if (response.status().isSuccess()) {
                        return (D) transcoder.newDocument(document.id(), 0, null, response.cas());
                    }

                    switch(response.status()) {
                        case EXISTS:
                            throw new CASMismatchException();
                        case TEMPORARY_FAILURE:
                            throw new TemporaryFailureException();
                        case OUT_OF_MEMORY:
                            throw new CouchbaseOutOfMemoryException();
                        default:
                            throw new CouchbaseException(response.status().toString());
