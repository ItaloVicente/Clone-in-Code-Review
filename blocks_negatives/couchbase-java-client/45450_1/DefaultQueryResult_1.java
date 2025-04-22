    public Iterator<QueryRow> rows(long timeout, TimeUnit timeUnit) {
        return asyncQueryResult
            .rows()
            .map(new Func1<AsyncQueryRow, QueryRow>() {
                @Override
                public QueryRow call(AsyncQueryRow asyncQueryRow) {
                    return new DefaultQueryRow(asyncQueryRow.value());
                }
            })
            .timeout(timeout, timeUnit)
            .toBlocking()
            .getIterator();
