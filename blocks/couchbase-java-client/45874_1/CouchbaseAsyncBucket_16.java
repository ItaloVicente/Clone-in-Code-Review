
                    if (response.status().isSuccess()) {
                        return (D) transcoder.newDocument(document.id(), 0, null, response.cas());
                    }

                    switch(response.status()) {
                        case TOO_BIG:
                            throw new RequestTooBigException();
                        case NOT_STORED:
                            throw new DocumentDoesNotExistException();
                        case TEMPORARY_FAILURE:
                            throw new TemporaryFailureException();
                        case OUT_OF_MEMORY:
                            throw new CouchbaseOutOfMemoryException();
                        default:
                            throw new CouchbaseException(response.status().toString());
