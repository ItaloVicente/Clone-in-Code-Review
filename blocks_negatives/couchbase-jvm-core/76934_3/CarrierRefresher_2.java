                    Collections.shuffle(nodeInfos);

                    for (NodeInfo nodeInfo : nodeInfos) {
                        if (!isValidCarrierNode(environment.sslEnabled(), nodeInfo)) {
                            continue;
                        }

                        if (refreshSequence == null) {
                            refreshSequence = refreshAgainstNode(bucketName, nodeInfo.hostname());
                        } else {
                            refreshSequence = refreshSequence
                                .onErrorResumeNext(refreshAgainstNode(bucketName, nodeInfo.hostname()));
                        }
                    }

                    if (refreshSequence == null) {
                        LOGGER.debug("No node registered in the current configuration, skipping to refresh.");
