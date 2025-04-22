            .flatMap(new Func1<UpsertResponse, Observable<ReplaceResponse>>() {
                @Override
                public Observable<ReplaceResponse> call(UpsertResponse response) {
                 return cluster.send(new ReplaceRequest(key, Unpooled.copiedBuffer(content, CharsetUtil.UTF_8), 24234234L, bucket));
                }
            }).toBlockingObservable().single();
