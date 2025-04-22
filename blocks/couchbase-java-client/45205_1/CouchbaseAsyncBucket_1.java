                        }
                    });
                    boolean parseSuccess = response.status().isSuccess();

                    AsyncQueryResult r = new DefaultAsyncQueryResult(rows, info, errors, finalSuccess, parseSuccess);
                    return Observable.just(r);
