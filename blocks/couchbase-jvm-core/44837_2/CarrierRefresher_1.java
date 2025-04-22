                });
            } else {
                refreshSequence = refreshSequence.onErrorResumeNext(
                    refreshAgainstNode(bucketName, nodeInfo.hostname())
                );
            }
        }
