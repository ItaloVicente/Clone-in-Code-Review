    public Observable<Boolean> exists(final String id) {
        return deferAndWatch(new Func1<Subscriber, Observable<ObserveResponse>>() {
            @Override
            public Observable<ObserveResponse> call(Subscriber s) {
                ObserveRequest request = new ObserveRequest(id, 0, true, (short) 0, bucket);
                request.subscriber(s);
                return core.send(request);
            }
        })
            .map(new Func1<ObserveResponse, Boolean>() {
                @Override
                public Boolean call(ObserveResponse response) {
                    ByteBuf content = response.content();
                    if (content != null && content.refCnt() > 0) {
                        content.release();
                    }
