            })
            .doOnNext(new Action1<Boolean>() {
                @Override
                public void call(Boolean aBoolean) {
                    bucketCache.clear();
                }
