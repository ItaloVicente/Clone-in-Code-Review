    @Override
    public Observable<IndexInfo> watchIndexes(List<String> watchList, boolean watchPrimary, final long watchTimeout,
            final TimeUnit watchTimeUnit) {
        Set<String> watchSet = new HashSet<String>(watchList);
        if (watchPrimary) {
            watchSet.add(Index.PRIMARY_NAME);
        }

        return Observable.from(watchSet)
                .flatMap(new Func1<String, Observable<IndexInfo>>() {
                    @Override
                    public Observable<IndexInfo> call(String s) {
                        return watchIndex(s, watchTimeout, watchTimeUnit);
                    }
                })
                .compose(safeAbort(watchTimeout, watchTimeUnit, null));
    }

