            .isEmpty()
            .flatMap(new Func1<Boolean, Observable<DesignDocument>>() {
                @Override
                public Observable<DesignDocument> call(Boolean doesNotExist) {
                    if (!doesNotExist && !overwrite) {
                        return Observable.error(new DesignDocumentAlreadyExistsException("Document exists in " +
                            "production and not overwriting."));
