        this.allRows = Blocking.blockForSingle(rows
                .map(new Func1<AsyncQueryRow, QueryRow>() {
                    @Override
                    public QueryRow call(AsyncQueryRow asyncQueryRow) {
                        return new DefaultQueryRow(asyncQueryRow.value());
                    }
                })
                .toList(), 1, TimeUnit.SECONDS);
