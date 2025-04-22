    /**
     * A transformer (to be used with {@link Observable#compose(Observable.Transformer)}) that takes a N1QL query result
     * and inspects the status and errors.
     *
     * If the query succeeded, emits TRUE. If there is an error but it is only that the index exists, and ignoreIfExist
     * is true, emits FALSE. Otherwise the error is propagated as a CouchbaseException.
     */
    private static Observable.Transformer<AsyncN1qlQueryResult, Boolean> checkIndexCreation(final boolean ignoreIfExist,
            final String prefixMsg) {
        return new Observable.Transformer<AsyncN1qlQueryResult, Boolean>() {
