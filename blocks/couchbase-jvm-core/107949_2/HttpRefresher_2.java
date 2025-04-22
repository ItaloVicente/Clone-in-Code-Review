                      });
              }
            })
            .onErrorResumeNext(new Func1<Throwable, Observable<BucketStreamingResponse>>() {
              @Override
              public Observable<BucketStreamingResponse> call(Throwable throwable) {
                LOGGER.warn("Could not fetch config from terse http, trying with verbose", throwable);
                LOGGER.warn("Starting to fetch verbose config from " + VERBOSE_PATH + "/" + name);

