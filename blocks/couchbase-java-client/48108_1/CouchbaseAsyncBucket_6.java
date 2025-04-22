                    public Observable<GetResponse> call(final BinaryRequest req) {
                        return Buffers.wrapColdWithAutoRelease(Observable.defer(new Func0<Observable<GetResponse>>() {
                            @Override
                            public Observable<GetResponse> call() {
                                return core.send(req);
                            }
                        }));
