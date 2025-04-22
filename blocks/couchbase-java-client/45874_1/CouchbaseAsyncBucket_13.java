
                    if (response.status().isSuccess()) {
                        return JsonLongDocument.create(id, expiry, response.value(), response.cas());
                    }

                    switch(response.status()) {
                        case TEMPORARY_FAILURE:
                            throw new TemporaryFailureException();
                        case OUT_OF_MEMORY:
                            throw new CouchbaseOutOfMemoryException();
                        default:
                            throw new CouchbaseException(response.status().toString());
                    }
