            }).onErrorResumeNext(new Func1<Throwable, Observable<AsyncBucket>>() {
                @Override
                public Observable<AsyncBucket> call(final Throwable throwable) {
                    if (throwable instanceof ConfigurationException) {
                        if (throwable.getCause() instanceof IllegalStateException
                            && throwable.getCause().getMessage().contains("NOT_EXISTS")) {
                            return Observable.error(new BucketDoesNotExistException("Bucket \"" + name
                                + "\" does not exist."));
                        } else if (throwable.getCause() instanceof IllegalStateException
                            && throwable.getCause().getMessage().contains("Unauthorized")) {
                            return Observable.error(new InvalidPasswordException("Passwords for bucket \"" + name
                                + "\" do not match."));
                        } else {
                            return Observable.error(throwable);
                        }
                    } else if (throwable instanceof CouchbaseException) {
                        return Observable.error(throwable);
                    } else {
                        return Observable.error(new CouchbaseException(throwable));
                    }
                }
            });
