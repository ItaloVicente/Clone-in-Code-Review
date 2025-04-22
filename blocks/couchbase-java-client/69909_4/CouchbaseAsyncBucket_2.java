                            if (mutationOptionBuilder.createDocument()) {
                                return insert(JsonArrayDocument.create(docId, mutationOptionBuilder.expiry(),
                                        JsonArray.create().add(element)),
                                        mutationOptionBuilder.persistTo(), mutationOptionBuilder.replicateTo())
                                        .map(ResultMappingUtils.getMapFullArrayDocResultToSubDocFn(mutationOperation))
                                        .onErrorResumeNext(retryIfDocExists);
                            } else {
                                return Observable.error(throwable);
                            }
