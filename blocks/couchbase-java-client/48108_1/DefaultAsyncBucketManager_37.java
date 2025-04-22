
        return Buffers.wrapColdWithAutoRelease(Observable.defer(new Func0<Observable<UpsertDesignDocumentResponse>>() {
            @Override
            public Observable<UpsertDesignDocumentResponse> call() {
                return core.send(req);
            }
        }))
        .map(new Func1<UpsertDesignDocumentResponse, DesignDocument>() {
            @Override
            public DesignDocument call(UpsertDesignDocumentResponse response) {
                try {
                    if (!response.status().isSuccess()) {
                        String msg = response.content().toString(CharsetUtil.UTF_8);
                        throw new DesignDocumentException("Could not store DesignDocument: " + msg);
                    }
                } finally {
                    if (response.content() != null && response.content().refCnt() > 0) {
                        response.content().release();
