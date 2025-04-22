                            return insert(JsonDocument.create(docId, mutationOptionBuilder.expiry(),
                                    JsonObject.create().put(key, value)),
                                    mutationOptionBuilder.persistTo(),
                                    mutationOptionBuilder.replicateTo())
                                    .map(ResultMappingUtils.getMapFullDocResultToSubDocFn(mutationOperation))
                                    .onErrorResumeNext(retryAddIfDocExists);
