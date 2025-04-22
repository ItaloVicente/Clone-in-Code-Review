                });
            } else {
                refreshSequence = refreshSequence.onErrorResumeNext(
                    refreshAgainstNode(bucketName, nodeInfo.hostname())
                );
            }
        }

        if (refreshSequence == null) {
            LOGGER.debug("Cannot poll bucket, because node list contains no nodes.");
            return;
        }

        refreshSequence.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                LOGGER.debug("Completed polling for bucket \"{}\".", bucketName);
            }
