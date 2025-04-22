            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                LOGGER.error("Error while subscribing to Http refresh stream!", e);
            }

            @Override
            public void onNext(ProposedBucketConfigContext ctx) {
