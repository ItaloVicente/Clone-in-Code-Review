        return deferAndWatch(new Func1<Subscriber, Observable<GetResponse>>() {
                @Override
                public Observable<GetResponse> call(Subscriber s) {
                    GetRequest request = new GetRequest(id, bucket);
                    request.subscriber(s);
                    return core.send(request);
                }
            })
            .filter(new Func1<GetResponse, Boolean>() {
                @Override
                public Boolean call(GetResponse response) {
                    if (response.status().isSuccess()) {
                        return true;
                    }
                    ByteBuf content = response.content();
                    if (content != null && content.refCnt() > 0) {
                        content.release();
                    }
