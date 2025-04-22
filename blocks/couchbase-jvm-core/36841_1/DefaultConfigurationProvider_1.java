            }).flatMap(new Func1<AddNodeResponse, Observable<AddServiceResponse>>() {
                @Override
                public Observable<AddServiceResponse> call(AddNodeResponse response) {
                    return cluster.send(new AddServiceRequest(ServiceType.BINARY, bucket, response.hostname()));
                }
            }).flatMap(new Func1<AddServiceResponse, Observable<GetBucketConfigResponse>>() {
                @Override
                public Observable<GetBucketConfigResponse> call(AddServiceResponse response) {
                    GetBucketConfigRequest request = new GetBucketConfigRequest(bucket, response.hostname());
                    return cluster.send(request);
                }
            }).map(new Func1<GetBucketConfigResponse, BucketConfig>() {
                @Override
                public BucketConfig call(GetBucketConfigResponse response) {
                    try {
                        String config = response.content().replace("$HOST", response.hostname());
                        return objectMapper.readValue(config, BucketConfig.class);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
