                        try {
                            Object value = responseStatusDocIdAndPathToValueEvaluator.call(responseStatus, docId, spec.path());
                            SubdocOperationResult<Mutation> singleResult = SubdocOperationResult
                                .createResult(spec.path(), spec.type(), response.status(), value);
                            return new DocumentFragment<Mutation>(docId, response.cas(), response.mutationToken(), Collections.singletonList(singleResult));
                        } catch (SubDocumentException e) {
