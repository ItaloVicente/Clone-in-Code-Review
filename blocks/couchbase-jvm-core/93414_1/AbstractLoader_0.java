                    ).onErrorResumeNext(new Func1<Throwable, Observable<AddServiceResponse>>() {
                        @Override
                        public Observable<AddServiceResponse> call(Throwable throwable) {
                            LOGGER.debug("Could not add service on {} because of {}, removing it again.",
                                node, throwable);
                            return cluster.<RemoveServiceResponse>send(new RemoveServiceRequest(serviceType, bucket, node))
                                .map(new Func1<RemoveServiceResponse, AddServiceResponse>() {
                                    @Override
                                    public AddServiceResponse call(RemoveServiceResponse removeServiceResponse) {
                                        return new AddServiceResponse(ResponseStatus.FAILURE, response.hostname());
                                    }
                                });
                        }
                    });
