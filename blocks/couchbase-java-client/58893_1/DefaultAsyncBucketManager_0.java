                                                        if (errors.size() == 1 && errors.get(0).getString("msg").contains("not found")) {
                                                            if (ignoreIfNotExist) {
                                                                return Observable.just(false);
                                                            } else {
                                                                return Observable.error(new IndexDoesNotExistException(errorPrefix));
                                                            }
