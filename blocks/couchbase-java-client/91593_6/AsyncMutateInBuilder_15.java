                            ResponseStatus responseStatus = response.status();
                            if (!responseStatus.isSuccess()) {
                                CouchbaseException subdocError = SubdocHelper.commonSubdocErrors(response.status(), docId, spec.path());
                                if (SubdocHelper.isSubdocLevelError(responseStatus)) {
                                    throw new MultiMutationException(0, responseStatus, Collections.singletonList(spec), subdocError);
                                } else {
                                    throw subdocError;
                                }
                            }

                            SubdocOperationResult<Mutation> singleResult = SubdocOperationResult
