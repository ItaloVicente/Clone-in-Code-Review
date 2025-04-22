                    final String bucketName = config.name();
                    Observable<String> refreshSequence = null;
                    for (NodeInfo nodeInfo : config.nodes()) {
                        if (refreshSequence == null) {
                            refreshSequence = refreshAgainstNode(bucketName, nodeInfo.hostname());
                        } else {
                            refreshSequence = refreshSequence
                                .onErrorResumeNext(refreshAgainstNode(bucketName, nodeInfo.hostname()));
                        }
                    }
