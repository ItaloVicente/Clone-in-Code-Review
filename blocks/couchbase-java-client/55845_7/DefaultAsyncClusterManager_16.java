                .doOnNext(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean exists) {
                        if (exists) {
                            throw new BucketAlreadyExistsException("Bucket " + settings.name() + " already exists!");
                        }
