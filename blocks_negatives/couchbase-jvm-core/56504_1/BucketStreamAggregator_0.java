        return open(aggregatorState)
                .flatMap(new Func1<StreamRequestResponse, Observable<DCPRequest>>() {
                             @Override
                             public Observable<DCPRequest> call(StreamRequestResponse response) {
                                 return response.stream();
                             }
