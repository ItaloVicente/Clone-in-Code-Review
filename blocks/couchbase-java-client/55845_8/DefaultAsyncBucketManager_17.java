                })
                .flatMap(new Func1<DesignDocument, Observable<DesignDocument>>() {
                    @Override
                    public Observable<DesignDocument> call(DesignDocument designDocument) {
                        return upsertDesignDocument(designDocument);
                    }
                });
