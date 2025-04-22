        final List<GetResponse> responses = Observable
                .from(keys)
                .flatMap(new Func1<String, Observable<GetResponse>>() {
                    @Override
                    public Observable<GetResponse> call(String key) {
                        return cluster().send(new GetRequest(key, bucket()));
                    }
                })
                .toList()
                .toBlocking()
                .single();
