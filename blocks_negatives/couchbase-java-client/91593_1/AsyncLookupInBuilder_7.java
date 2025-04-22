                return core.send(request);
            }
        }).map(new Func1<SimpleSubdocResponse, DocumentFragment<Lookup>>() {
            @Override
            public DocumentFragment<Lookup> call(SimpleSubdocResponse response) {
                if (response.status().isSuccess()) {
                    try {
                        long count = subdocumentTranscoder.decode(response.content(), Long.class);
                        SubdocOperationResult<Lookup> single = SubdocOperationResult
                            .createResult(spec.path(), Lookup.GET_COUNT, response.status(), count);
                        return new DocumentFragment<Lookup>(id, response.cas(), response.mutationToken(),
                            Collections.singletonList(single));
                    } finally {
                        if (response.content() != null) {
                            response.content().release();
                        }
                    }
                } else {
                    if (response.content() != null && response.content().refCnt() > 0) {
                        response.content().release();
