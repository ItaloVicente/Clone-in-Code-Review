            public Observable<IndexInfo> call(Observable<IndexInfo> source) {
                return source.timeout(watchTimeout, watchTimeUnit)
                .onErrorResumeNext(new Func1<Throwable, rx.Observable<IndexInfo>>() {
                    @Override
                    public rx.Observable<IndexInfo> call(Throwable t) {
                        if (t instanceof TimeoutException) {
                            if (indexName == null) {
                                INDEX_WATCH_LOG.debug("Watched indexes were not all online after the given {} {}", watchTimeout, watchTimeUnit);
                            } else {
                                INDEX_WATCH_LOG.debug("Index {} was not online after the given {} {}", indexName, watchTimeout, watchTimeUnit);
                            }
                            return Observable.empty();
                        }
                        if (t instanceof CannotRetryException && t.getCause() instanceof IndexesNotReadyException) {
                            INDEX_WATCH_LOG.debug("{} after {} attempts", INDEX_WATCH_MAX_ATTEMPTS, t.getCause().getMessage());
                            return Observable.empty();
                        }
                        return rx.Observable.error(t);
                    }
                });
