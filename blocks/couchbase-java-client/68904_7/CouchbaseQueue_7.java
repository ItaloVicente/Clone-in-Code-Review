                            if (throwable instanceof MultiMutationException) {
                                ResponseStatus status = ((MultiMutationException) throwable).firstFailureStatus();
                                List<SubdocOperationResult<Mutation>> list;
                                list = new ArrayList<SubdocOperationResult<Mutation>>();
                                list.add(SubdocOperationResult.createResult(null, Mutation.ARRAY_PUSH_LAST,
                                        status, null));
                                return Observable.just(new DocumentFragment<Mutation>(null, 0,
                                        null,
                                        list));
                            } else {
                                return Observable.error(throwable);
                            }
