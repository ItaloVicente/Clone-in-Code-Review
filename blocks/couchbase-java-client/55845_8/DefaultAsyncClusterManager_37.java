                .doOnNext(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean exists) {
                        if (!exists) {
                            throw new BucketDoesNotExistException("Bucket " + settings.name() + " does not exist!");
                        }
                    }
                }).flatMap(new Func1<Boolean, Observable<UpdateBucketResponse>>() {
                    @Override
                    public Observable<UpdateBucketResponse> call(Boolean exists) {
                        return core.send(new UpdateBucketRequest(settings.name(), sb.toString(), username, password));
