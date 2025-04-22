    public Observable<DesignDocument> getDesignDocument(String name, boolean development) {
        return core.<GetDesignDocumentResponse>send(new GetDesignDocumentRequest(name, development, bucket, password))
            .filter(new Func1<GetDesignDocumentResponse, Boolean>() {
                @Override
                public Boolean call(GetDesignDocumentResponse response) {
                    boolean success = response.status().isSuccess();
                    if (!success) {
                        if (response.content() != null && response.content().refCnt() > 0) {
                           response.content().release();
                        }
