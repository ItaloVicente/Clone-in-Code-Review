                .onErrorResumeNext(new Func1<Throwable, rx.Observable<IndexInfo>>() {
                    @Override
                    public rx.Observable<IndexInfo> call(Throwable t) {
                        if (t instanceof TimeoutException) {
                            if (indexName == null) {
                                INDEX_WATCH_LOG.debug("Watched indexes were not all online after the given {} {}", watchTimeout, watchTimeUnit);
                            } else {
                                INDEX_WATCH_LOG.debug("Index {} was not online after the given {} {}", indexName, watchTimeout, watchTimeUnit);
