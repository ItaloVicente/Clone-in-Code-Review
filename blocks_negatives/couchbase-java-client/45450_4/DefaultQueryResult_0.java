    @Override
    public List<QueryRow> allRows(long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncQueryResult
            .rows()
            .map(new Func1<AsyncQueryRow, QueryRow>() {
                @Override
                public QueryRow call(AsyncQueryRow asyncQueryRow) {
                    return new DefaultQueryRow(asyncQueryRow.value());
                }
            })
            .toList(), timeout, timeUnit);
    }
