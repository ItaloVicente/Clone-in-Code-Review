                                })
                                .toList()
                                .flatMap(new Func1<List<StreamRequestResponse>, Observable<DCPRequest>>() {
                                    @Override
                                    public Observable<DCPRequest> call(List<StreamRequestResponse> streamRequestResponses) {
                                        return response.connection().subject();
                                    }
