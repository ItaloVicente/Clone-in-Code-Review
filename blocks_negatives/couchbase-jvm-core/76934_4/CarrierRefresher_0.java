            });


        Observable<String> refreshSequence = null;
        List<NodeInfo> nodeInfos = new ArrayList<NodeInfo>(config.nodes());
        Collections.shuffle(nodeInfos);
        for (final NodeInfo nodeInfo : nodeInfos) {
            if (!isValidCarrierNode(environment.sslEnabled(), nodeInfo)) {
                continue;
            }

            if (refreshSequence == null) {
                refreshSequence = pollSequence.flatMap(new Func1<Long, Observable<String>>() {
                    @Override
                    public Observable<String> call(Long aLong) {
                        return refreshAgainstNode(bucketName, nodeInfo.hostname());
