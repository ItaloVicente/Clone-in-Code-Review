                AbstractSubdocMutationRequest request = requestFactory.call(spec, buf);
                request.subscriber(s);
                return core.send(request);
            }
        }).map(new Func1<SimpleSubdocResponse, DocumentFragment<Mutation>>() {
            @Override
            public DocumentFragment<Mutation> call(SimpleSubdocResponse response) {
                if (response.content() != null && response.content().refCnt() > 0) {
                    response.content().release();
                }

                ResponseStatus responseStatus = response.status();
                try {
                    Object value = responseStatusDocIdAndPathToValueEvaluator.call(responseStatus, docId, spec.path());
                    SubdocOperationResult<Mutation> singleResult = SubdocOperationResult
                        .createResult(spec.path(), spec.type(), response.status(), value);
                    return new DocumentFragment<Mutation>(docId, response.cas(), response.mutationToken(), Collections.singletonList(singleResult));
                } catch (SubDocumentException e) {
                    if (SubdocHelper.isSubdocLevelError(responseStatus)) {
                        throw new MultiMutationException(0, responseStatus, Collections.singletonList(spec), e);
                    } else {
                        throw e;
