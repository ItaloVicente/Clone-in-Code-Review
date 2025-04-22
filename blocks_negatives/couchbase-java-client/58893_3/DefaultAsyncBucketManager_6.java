            .filter(new Func1<IndexInfo, Boolean>() {
                @Override
                public Boolean call(IndexInfo indexInfo) {
                    return indexInfo.state().equals("pending");
                }
            })
            .map(new Func1<IndexInfo, String>() {
                @Override
                public String call(IndexInfo indexInfo) {
                    return indexInfo.name();
                }
            })
            .toList()
            .flatMap(new Func1<List<String>, Observable<List<String>>>() {
                @Override
                public Observable<List<String>> call(final List<String> pendingIndexes) {
                    if (pendingIndexes.isEmpty()) {
                        return Observable.just(pendingIndexes);
