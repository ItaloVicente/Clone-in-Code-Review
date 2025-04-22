        return listIndexes()
                .flatMap(new Func1<IndexInfo, Observable<IndexInfo>>() {
                    @Override
                    public Observable<IndexInfo> call(IndexInfo i) {
                        if (!watchSet.contains(i.name())) {
                            return Observable.empty();
                        } else if (!"online".equals(i.state()))
                            return Observable.error(new IndexesNotReadyException("Index not ready: " + i.name()));
                        else {
                            return Observable.just(i);
                        }
                    }
                })
                .doOnEach(new Action1<Notification<? super IndexInfo>>() {
                    @Override
                    public void call(Notification<? super IndexInfo> notification) {
                        if (INDEX_WATCH_LOG.isDebugEnabled()) {
                            if (notification.isOnNext()) {
                                IndexInfo info = (IndexInfo) notification.getValue();
                                String indexShortInfo = info.name() + "(" + info.state() + ")";
                                INDEX_WATCH_LOG.debug("Index ready: " + indexShortInfo);
                            } else if (notification.isOnError()) {
                                Throwable e = notification.getThrowable();
                                if (e instanceof IndexesNotReadyException) {
                                    INDEX_WATCH_LOG.debug("Will retry: " + e.getMessage());
                                }
                            }
                        }
                    }
                })
                .retryWhen(anyOf(IndexesNotReadyException.class)
                        .delay(INDEX_WATCH_DELAY)
                        .max(INDEX_WATCH_MAX_ATTEMPTS)
                        .build())
                .compose(safeAbort(watchTimeout, watchTimeUnit, null));
