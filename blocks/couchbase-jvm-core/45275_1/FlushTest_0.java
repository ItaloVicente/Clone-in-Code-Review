        Observable
                .from(keys)
                .flatMap(new Func1<String, Observable<UpsertResponse>>() {
                    @Override
                    public Observable<UpsertResponse> call(String key) {
                        return cluster().send(new UpsertRequest(key, Unpooled.copiedBuffer("Content", CharsetUtil.UTF_8), bucket()));
                    }
                })
                .doOnNext(new Action1<UpsertResponse>() {
                    @Override
                    public void call(UpsertResponse upsertResponse) {
                        ReferenceCountUtil.releaseLater(upsertResponse.content());
                    }
                })
                .toBlocking()
                .last();
