
        if (builder.requestBufferWaitStrategy == null) {
            requestBufferWaitStrategy = new WaitStrategyFactory() {
                @Override
                public WaitStrategy newWaitStrategy() {
                    return new BlockingWaitStrategy();
                }
            };
        } else {
            requestBufferWaitStrategy = builder.requestBufferWaitStrategy;
        }
