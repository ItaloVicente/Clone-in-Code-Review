                if (response.status().isSuccess()) {
                    SubdocOperationResult<Lookup> result = SubdocOperationResult
                            .createResult(spec.path(), Lookup.EXIST, response.status(), true);
                    return new DocumentFragment<Lookup>(docId, response.cas(), response.mutationToken(), Collections.singletonList(result));
                } else if (response.status() == ResponseStatus.SUBDOC_PATH_NOT_FOUND) {
                    SubdocOperationResult<Lookup> result = SubdocOperationResult
                            .createResult(spec.path(), Lookup.EXIST, response.status(), false);
                    return new DocumentFragment<Lookup>(docId, response.cas(), response.mutationToken(), Collections.singletonList(result));
                }
