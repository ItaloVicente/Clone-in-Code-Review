                .<OpenConnectionResponse>send(new OpenConnectionRequest(name, bucket))
                .flatMap(new Func1<OpenConnectionResponse, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(OpenConnectionResponse openConnectionResponse) {
                        return partitionSize();
                    }
                })
                .flatMap(new Func1<Integer, Observable<StreamRequestResponse>>() {
