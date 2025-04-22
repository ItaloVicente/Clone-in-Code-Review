            }).flatMap(new Func1<Long, Observable<String>>() {
                @Override
                public Observable<String> call(Long aLong) {
                    List<NodeInfo> nodeInfos = new ArrayList<NodeInfo>(config.nodes());
                    if (nodeInfos.isEmpty()) {
                        LOGGER.debug("Cannot poll bucket, because node list contains no nodes.");
                        return Observable.empty();
                    }
                    shiftNodeList(nodeInfos);
                    return buildRefreshFallbackSequence(nodeInfos, bucketName);
                }
            }).subscribe(new Subscriber<String>() {
                @Override
                public void onCompleted() {
                    LOGGER.debug("Completed polling for bucket \"{}\".", bucketName);
                }
