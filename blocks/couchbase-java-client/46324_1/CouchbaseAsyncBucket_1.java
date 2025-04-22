                    if (response.status().isSuccess()) {
                        return (D) transcoder.newDocument(document.id(), document.expiry(), document.content(),
                                response.cas());
                    }

                    switch (response.status()) {
                        case TOO_BIG:
                            throw new RequestTooBigException();
                        case NOT_EXISTS:
                            throw new DocumentDoesNotExistException();
                        case EXISTS:
                            throw new CASMismatchException();
                        case TEMPORARY_FAILURE:
                            throw new TemporaryFailureException();
                        case OUT_OF_MEMORY:
                            throw new CouchbaseOutOfMemoryException();
                        default:
                            throw new CouchbaseException(response.status().toString());
                    }
