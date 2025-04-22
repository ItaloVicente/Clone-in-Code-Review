                            if (response.status().isSuccess()) {
                                int resultSize = response.responses().size();
                                List<SubdocOperationResult<Mutation>> results = new ArrayList<SubdocOperationResult<Mutation>>(resultSize);
                                for (MultiResult<Mutation> result : response.responses()) {
                                    try {
                                        Object content = null;
                                        if (result.value().isReadable()) {
                                            content = subdocumentTranscoder.decode(result.value(), Object.class);
                                        }
                                        results.add(SubdocOperationResult
                                            .createResult(result.path(), result.operation(), result.status(), content));
                                    } catch (TranscodingException e) {
                                        LOGGER.error(
                                            "Couldn't decode multi-mutation {} for {}/{}",
                                            user(result),
                                            user(docId),
                                            user(result.path()),
                                            e
                                        );
                                        results.add(SubdocOperationResult.createFatal(result.path(), result.operation(), e));
                                    } finally {
                                        if (result.value() != null) {
                                            result.value().release();
                                        }
                                    }
                                }
                                return Observable.just(
                                    new DocumentFragment<Mutation>(docId, response.cas(), response.mutationToken(), results));
                            }

                            switch(response.status()) {
                                case SUBDOC_MULTI_PATH_FAILURE:
                                    int index = response.firstErrorIndex();
                                    ResponseStatus errorStatus = response.firstErrorStatus();
                                    String errorPath = mutationSpecs.get(index).path();
                                    CouchbaseException errorException = SubdocHelper.commonSubdocErrors(errorStatus, docId, errorPath);

                                    return Observable
                                        .error(new MultiMutationException(index, errorStatus, mutationSpecs, errorException));
                                default:
                                    return Observable.error(SubdocHelper.commonSubdocErrors(response.status(), docId, "MULTI-MUTATION"));
                            }
                        }
                    }), req, environment, timeout, timeUnit);
