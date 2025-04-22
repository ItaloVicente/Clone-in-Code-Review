    public Iterator<ViewRow> rows(long timeout, TimeUnit timeUnit) {
        return asyncViewResult
            .rows()
            .map(new Func1<AsyncViewRow, ViewRow>() {
                @Override
                public ViewRow call(AsyncViewRow asyncViewRow) {
                    return new DefaultViewRow(env, bucket, asyncViewRow.id(), asyncViewRow.key(), asyncViewRow.value());
                }
            })
            .timeout(timeout, timeUnit)
            .toBlocking()
            .getIterator();
