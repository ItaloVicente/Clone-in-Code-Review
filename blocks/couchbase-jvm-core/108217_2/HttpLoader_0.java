                    if (response.status() == ResponseStatus.NOT_EXISTS) {
                        LOGGER.debug("Terse bucket config failed (not found), falling back to verbose.");
                        return cluster().send(new BucketConfigRequest(VERBOSE_PATH, hostname, bucket, username, password));
                    } else {
                        LOGGER.debug("Terse bucket config failed, propagating failed response");
                        return Observable.just(response);
                    }
