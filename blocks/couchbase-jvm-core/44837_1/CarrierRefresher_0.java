        LOGGER.debug("Config for bucket \"" + bucketName + "\" marked as tainted, starting polling.");
        subscriptions.add(bucketName);
        Observable<Long> pollSequence = Observable
            .interval(1, TimeUnit.SECONDS)
            .takeWhile(new Func1<Long, Boolean>() {
                @Override
                public Boolean call(Long aLong) {
                    return subscriptions.contains(bucketName);
                }
            });


        Observable<String> refreshSequence = null;
        for (final NodeInfo nodeInfo : config.nodes()) {
            if (refreshSequence == null) {
                refreshSequence = pollSequence.flatMap(new Func1<Long, Observable<String>>() {
