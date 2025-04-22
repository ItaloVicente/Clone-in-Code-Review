                  .<BucketStreamingResponse>send(new BucketStreamingRequest(VERBOSE_PATH, name, username, password))
                  .flatMap(new Func1<BucketStreamingResponse, Observable<BucketStreamingResponse>>() {
                    @Override
                    public Observable<BucketStreamingResponse> call(BucketStreamingResponse response) {
                        LOGGER.warn("Got verbose response: " + response);

                        if (response.status().isSuccess()) {
                        return Observable.just(response);
                      }
                      throw new ConfigurationException("Could not load verbose http config: " + response.status());
                    }
                  });
              }
            });
