        if (!query.params().hasServerSideTimeout()) {
            query.params().serverSideTimeout(timeout, timeUnit);
        }

        return Blocking.blockForSingle(asyncBucket
            .query(query)
