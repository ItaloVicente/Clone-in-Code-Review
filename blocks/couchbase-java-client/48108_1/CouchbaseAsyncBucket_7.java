            incoming = Buffers.wrapColdWithAutoRelease(Observable.defer(new Func0<Observable<GetResponse>>() {
                @Override
                public Observable<GetResponse> call() {
                    return core.send(new ReplicaGetRequest(id, bucket, (short) type.ordinal()));
                }
            }));
