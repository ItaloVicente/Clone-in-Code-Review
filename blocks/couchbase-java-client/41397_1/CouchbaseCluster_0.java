        return core
                .<SeedNodesResponse>send(new SeedNodesRequest(seedNodes))
                .flatMap(new Func1<SeedNodesResponse, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> call(SeedNodesResponse seedNodesResponse) {
                        return Observable.just(seedNodesResponse.status().isSuccess());
                    }
                });
