                return core.send(request);
            }
        }).map(new Func1<SimpleSubdocResponse, DocumentFragment<Lookup>>() {
            @Override
            public DocumentFragment<Lookup> call(SimpleSubdocResponse response) {
                if (response.content() != null && response.content().refCnt() > 0) {
                    response.content().release();
                }
