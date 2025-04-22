                public Observable<? extends D> call(InsertResponse response) {
                    if (response.status() == ResponseStatus.EXISTS) {
                        return Observable.error(new DocumentAlreadyExistsException());
                    }
                    return Observable.just((D) converter.newDocument(document.id(), document.content(), response.cas(),
                        document.expiry(), response.status()));
