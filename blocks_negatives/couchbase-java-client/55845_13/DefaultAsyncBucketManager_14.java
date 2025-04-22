                .flatMap(new Func1<String, Observable<IndexInfo>>() {
                    @Override
                    public Observable<IndexInfo> call(String s) {
                        return watchIndex(s, watchTimeout, watchTimeUnit);
                    }
                })
                .compose(safeAbort(watchTimeout, watchTimeUnit, null));
