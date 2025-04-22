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
        List<NodeInfo> nodeInfos = new ArrayList<NodeInfo>(config.nodes());
        Collections.shuffle(nodeInfos);
        for (final NodeInfo nodeInfo : nodeInfos) {
            if (refreshSequence == null) {
                refreshSequence = pollSequence.flatMap(new Func1<Long, Observable<String>>() {
