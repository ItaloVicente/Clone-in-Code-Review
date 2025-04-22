        this.finalSuccess = Blocking.blockForSingle(finalSuccess, timeout, timeUnit);

        this.allRows = Blocking.blockForSingle(rows
                .map(new Func1<AsyncQueryRow, QueryRow>() {
                    @Override
                    public QueryRow call(AsyncQueryRow asyncQueryRow) {
                        return new DefaultQueryRow(asyncQueryRow.value());
                    }
                })
                .toList(), 1, TimeUnit.SECONDS);

        this.errors = Blocking.blockForSingle(errors.toList(), 1, TimeUnit.SECONDS);
        this.info = Blocking.blockForSingle(info.singleOrDefault(JsonObject.empty()), 1, TimeUnit.SECONDS);
