                })
                .toList()
                .flatMap(new Func1<List<String>, Observable<List<String>>>() {
                    @Override
                    public Observable<List<String>> call(final List<String> pendingIndexes) {
                        if (pendingIndexes.isEmpty()) {
                            return Observable.just(pendingIndexes);
