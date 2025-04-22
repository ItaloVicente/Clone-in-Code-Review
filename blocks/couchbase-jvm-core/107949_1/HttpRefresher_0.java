        Observable<BucketStreamingResponse> response = super
            .registerBucket(name, username, password)
            .flatMap(new Func1<Boolean, Observable<BucketStreamingResponse>>() {
              @Override
              public Observable<BucketStreamingResponse> call(Boolean aBoolean) {
                  LOGGER.trace("Starting to fetch terse config from " + TERSE_PATH + "/" + name);

                  return cluster()
                      .<BucketStreamingResponse>send(new BucketStreamingRequest(TERSE_PATH, name, username, password))
                      .flatMap(new Func1<BucketStreamingResponse, Observable<BucketStreamingResponse>>() {
