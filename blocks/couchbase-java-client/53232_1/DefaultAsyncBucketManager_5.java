    public Observable<Boolean> removeDesignDocument(final String name, final boolean development) {
        return Observable.defer(new Func0<Observable<RemoveDesignDocumentResponse>>() {
            @Override
            public Observable<RemoveDesignDocumentResponse> call() {
                return core.send(new RemoveDesignDocumentRequest(name, development, bucket, password));
            }
        }).map(new Func1<RemoveDesignDocumentResponse, Boolean>() {
