    public Observable<IndexInfo> watchIndexes(List<String> watchList, boolean watchPrimary, final long watchTimeout,
            final TimeUnit watchTimeUnit) {
        final Set<String> watchSet = new HashSet<String>(watchList);
        if (watchPrimary) {
            watchSet.add(Index.PRIMARY_NAME);
        }

