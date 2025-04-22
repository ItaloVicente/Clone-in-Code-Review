    public Observable<QueryResult> query(final Query query) {
        if (!query.hasFrom()) {
            query.from(bucket);
        }
        return query(query.toString());
    }

    @Override
    public Observable<QueryResult> query(final String query) {
