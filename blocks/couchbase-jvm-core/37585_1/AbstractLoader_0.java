                public Observable<AddServiceResponse> call(final AddNodeResponse response) {
                    if (!response.status().isSuccess()) {
                        return Observable.error(new IllegalStateException("Could not add node for config loading."));
                    }
                    return cluster.send(
                            new AddServiceRequest(type, bucket, password, port(environment), response.hostname())
                    );
