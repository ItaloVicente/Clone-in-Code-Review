                                public Observable<QueryPlan> call(ArrayList<Throwable> errors) {
                                    if (errors.size() == 1) {
                                        return Observable.error(new CouchbaseException(
                                                "Error while preparing plan", errors.get(0)));
                                    } else {
                                        return Observable.error(new CompositeException(
                                                "Multiple errors while preparing plan", errors));
                                    }
