                @Override
                public void onNext(String rawConfig) {
                    if (rawConfig.startsWith("{")) {
                        provider().proposeBucketConfig(bucketName, rawConfig);
                    }
                }
            });
    }

    private Observable<String> buildRefreshFallbackSequence(List<NodeInfo> nodeInfos, String bucketName) {
        Observable<String> failbackSequence = null;
