                }
            })
            .retryWhen(anyOf(IndexesNotReadyException.class)
                .delay(INDEX_WATCH_DELAY)
                .max(INDEX_WATCH_MAX_ATTEMPTS)
                .build())
            .compose(safeAbort(watchTimeout, watchTimeUnit, null));
