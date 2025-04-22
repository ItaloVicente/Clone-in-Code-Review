            .flatMap(new Func1<IndexInfo, Observable<IndexInfo>>() {
                @Override
                public Observable<IndexInfo> call(IndexInfo i) {
                    if (!indexName.equals(i.name())) {
                        return Observable.empty();
                    } else if (!"online".equals(i.state()))
                        return Observable.error(new IndexesNotReadyException("Index not ready: " + i.name()));
                    else {
                        return Observable.just(i);
