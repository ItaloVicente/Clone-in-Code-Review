                                                        public Observable<Boolean> call(List<JsonObject> errors) {
                                                            if (errors.size() == 1 && errors.get(0).getString("msg").contains("already exist")) {
                                                                if (ignoreIfExist) {
                                                                    return Observable.just(false);
                                                                } else {
                                                                    return Observable.error(new IndexAlreadyExistsException(prefixMsg));
                                                                }
