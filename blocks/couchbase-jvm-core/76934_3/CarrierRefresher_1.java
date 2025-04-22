                    shiftNodeList(nodeInfos);
                    return buildRefreshFallbackSequence(nodeInfos, bucketName);
                }
            }).subscribe(new Subscriber<String>() {
                @Override
                public void onCompleted() {
                    LOGGER.debug("Completed polling for bucket \"{}\".", bucketName);
                }
