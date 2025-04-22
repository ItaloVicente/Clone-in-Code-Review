        return Observable
            .from(seedHosts.get(), Schedulers.computation())
            .flatMap(new Func1<String, Observable<AddNodeResponse>>() {
                @Override
                public Observable<AddNodeResponse> call(String hostname) {
                    return cluster.send(new AddNodeRequest(hostname));
