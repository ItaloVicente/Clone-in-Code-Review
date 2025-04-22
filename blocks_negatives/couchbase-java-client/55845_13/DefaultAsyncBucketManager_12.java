                })
                .doOnEach(new Action1<Notification<? super IndexInfo>>() {
                    @Override
                    public void call(Notification<? super IndexInfo> notification) {
                        if (INDEX_WATCH_LOG.isDebugEnabled()) {
                            if (notification.isOnNext()) {
                                IndexInfo info = (IndexInfo) notification.getValue();
                                String indexShortInfo = indexName + "(" + info.state() + ")";
                                INDEX_WATCH_LOG.debug("Index ready: " + indexShortInfo);
                            } else if (notification.isOnError()) {
                                Throwable e = notification.getThrowable();
                                if (e instanceof IndexesNotReadyException) {
                                    INDEX_WATCH_LOG.debug("Will retry: " + e.getMessage());
                                }
