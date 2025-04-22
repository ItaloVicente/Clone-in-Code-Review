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
