
    @Override
    public Observable<Boolean> removeDesignDocument(DesignDocument designDocument) {
        return removeDesignDocument(designDocument.name(), designDocument.development());
    }

    @Override
    public Observable<Boolean> removeDesignDocument(final String name) {
        return Observable
            .from(true, false)
            .flatMap(new Func1<Boolean, Observable<? extends Boolean>>() {
                @Override
                public Observable<? extends Boolean> call(Boolean development) {
                    return removeDesignDocument(name, development);
                }
            }).last();
    }

    @Override
    public Observable<Boolean> removeDesignDocument(String name, boolean development) {
            .<RemoveDesignDocumentResponse>send(new RemoveDesignDocumentRequest(name, development))
            .map(new Func1<RemoveDesignDocumentResponse, Boolean>() {
                @Override
                public Boolean call(RemoveDesignDocumentResponse response) {
                    return true;
                }
            });*/
        return null;
    }

    @Override
    public Observable<DesignDocument> updateDesignDocument(DesignDocument designDocument) {
        return null;
    }

    @Override
    public Observable<DesignDocument> insertDesignDocument(DesignDocument designDocument) {
        return null;
    }

    @Override
    public Observable<DesignDocument> listDesignDocuments() {

        return null;
    }

    @Override
    public Observable<DesignDocument> getDesignDocument(final String name) {
        return Observable.from(true, false)
            .flatMap(new Func1<Boolean, Observable<GetDesignDocumentResponse>>() {
                @Override
                public Observable<GetDesignDocumentResponse> call(Boolean development) {
                    return core.send(new GetDesignDocumentRequest(name, development, bucket, password));
                }
            })
            .filter(new Func1<GetDesignDocumentResponse, Boolean>() {
                @Override
                public Boolean call(GetDesignDocumentResponse response) {
                    return response.status().isSuccess();
                }
            })
            .map(new Func1<GetDesignDocumentResponse, DesignDocument>() {
                @Override
                public DesignDocument call(GetDesignDocumentResponse response) {
                    return DesignDocument.from(name, response.development(), response.content().toString(CharsetUtil.UTF_8));
                }
            });
    }
