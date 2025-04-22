                        public Observable<BucketStreamingResponse> call(BucketStreamingResponse response) {
                            LOGGER.warn("Got terse response: " + response);

                          if (response.status().isSuccess()) {
                            return Observable.just(response);
                          }
                          throw new ConfigurationException("Could not load terse http config: " + response.status());
