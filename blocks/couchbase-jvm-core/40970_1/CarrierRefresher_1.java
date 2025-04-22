            .filter(new Func1<BucketConfig, Boolean>() {
                @Override
                public Boolean call(BucketConfig config) {
                    return registrations().containsKey(config.name());
                }
            })
