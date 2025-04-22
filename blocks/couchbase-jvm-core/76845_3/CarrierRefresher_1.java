            .filter(new Func1<BucketConfig, Boolean>() {
                @Override
                public Boolean call(BucketConfig config) {
                    boolean allowed = allowedToPoll();
                    if (allowed) {
                        lastPollTimestamp = System.nanoTime();
                    } else {
                        LOGGER.trace("Ignoring refresh polling attempt because poll interval is too small.");
                    }
                    return allowed;
                }
            })
