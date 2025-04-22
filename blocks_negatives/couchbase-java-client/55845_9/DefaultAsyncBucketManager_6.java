            .isEmpty()
            .flatMap(new Func1<Boolean, Observable<DesignDocument>>() {
                @Override
                public Observable<DesignDocument> call(Boolean doesNotExist) {
                    if (doesNotExist) {
                        return upsertDesignDocument(designDocument, development);
                    } else {
                        return Observable.error(new DesignDocumentAlreadyExistsException());
