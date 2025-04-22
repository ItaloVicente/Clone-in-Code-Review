    @Override
    public List<QueryRow> allRows() {
        return allRows(timeout, TIMEOUT_UNIT);
    }

    @Override
    public List<QueryRow> allRows(long timeout, TimeUnit timeUnit) {
        return asyncQueryResult
            .rows()
            .map(new Func1<AsyncQueryRow, QueryRow>() {
                @Override
                public QueryRow call(AsyncQueryRow asyncQueryRow) {
                    return new DefaultQueryRow(asyncQueryRow.value());
                }
            })
            .toList()
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
    }

    @Override
    public Iterator<QueryRow> rows() {
        return rows(timeout, TIMEOUT_UNIT);
    }

    @Override
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
