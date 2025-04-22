                    return getDesignDocument(name, true);
                }
            })
            .flatMap(new Func1<DesignDocument, Observable<DesignDocument>>() {
                @Override
                public Observable<DesignDocument> call(DesignDocument designDocument) {
                    return upsertDesignDocument(designDocument);
                }
            });
    }

    /*==== INDEX MANAGEMENT ====*/

    private static <T> Func1<List<JsonObject>, Observable<T>> errorsToThrowable(final String messagePrefix) {
        return new Func1<List<JsonObject>, Observable<T>>() {
            @Override
            public Observable<T> call(List<JsonObject> errors) {
                return Observable.<T>error(new CouchbaseException(messagePrefix + errors));
            }
        };
