        if (!query.params().deferred()) {
            return asyncBucket.query(query, timeout, timeUnit)
                    .flatMap(AnalyticsQueryExecutor.ASYNC_RESULT_TO_SYNC)
                    .toBlocking()
                    .single();
        } else {
            return asyncBucket.query(query, timeout, timeUnit)
                    .flatMap(AnalyticsQueryExecutor.ASYNC_RESULT_TO_SYNC_DEFERRED)
                    .toBlocking()
                    .single();
        }

