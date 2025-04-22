        final RemoveDesignDocumentRequest req = new RemoveDesignDocumentRequest(name, development, bucket, password);

        return Buffers.wrapColdWithAutoRelease(Observable.defer(new Func0<Observable<RemoveDesignDocumentResponse>>() {
            @Override
            public Observable<RemoveDesignDocumentResponse> call() {
                return core.send(req);
            }
        }))
        .map(new Func1<RemoveDesignDocumentResponse, Boolean>() {
            @Override
            public Boolean call(RemoveDesignDocumentResponse response) {
                if (response.content() != null && response.content().refCnt() > 0) {
                    response.content().release();
