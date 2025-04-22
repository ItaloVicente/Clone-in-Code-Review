                    final String bucketName = config.name();
                    Observable<String> refreshSequence = null;

                    List<NodeInfo> nodeInfos = new ArrayList<NodeInfo>(config.nodes());
                    Collections.shuffle(nodeInfos);
                    for (NodeInfo nodeInfo : nodeInfos) {
                        if (refreshSequence == null) {
                            refreshSequence = refreshAgainstNode(bucketName, nodeInfo.hostname());
                        } else {
                            refreshSequence = refreshSequence
                                .onErrorResumeNext(refreshAgainstNode(bucketName, nodeInfo.hostname()));
                        }
                    }
