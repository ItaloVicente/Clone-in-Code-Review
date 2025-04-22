                    if (flushResponse.status() == ResponseStatus.FAILURE) {
                        if (flushResponse.content().contains("disabled")) {
                            return Observable.error(new FlushDisabledException("Flush is disabled for this bucket."));
                        } else {
                            return Observable.error(new CouchbaseException("Flush failed because of: "
                                + flushResponse.content()));
                        }
                    }
