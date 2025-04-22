                return applyTimeout(deferAndWatch(new Func1<Subscriber, Observable<ObserveResponse>>() {
                    @Override
                    public Observable<ObserveResponse> call(Subscriber s) {
                        request.subscriber(s);
                        return core.send(request);
                    }
                }) .map(new Func1<ObserveResponse, Boolean>() {
                    @Override
                    public Boolean call(ObserveResponse response) {
                        ByteBuf content = response.content();
                        if (content != null && content.refCnt() > 0) {
                            content.release();
                        }
