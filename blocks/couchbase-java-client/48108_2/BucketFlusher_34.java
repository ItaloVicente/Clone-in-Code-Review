                public Observable<GetResponse> call(final String id) {
                    return Buffers.wrapColdWithAutoRelease(Observable.defer(new Func0<Observable<GetResponse>>() {
                        @Override
                        public Observable<GetResponse> call() {
                            return core.send(new GetRequest(id, bucket));
                        }
                    }));
