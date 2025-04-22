                public Observable<UpsertResponse> call(final String id) {
                    return Buffers.wrapColdWithAutoRelease(Observable.defer(new Func0<Observable<UpsertResponse>>() {
                        @Override
                        public Observable<UpsertResponse> call() {
                            return core.send(new UpsertRequest(id, Unpooled.copiedBuffer(id, CharsetUtil.UTF_8), bucket));
                        }
                    }));
