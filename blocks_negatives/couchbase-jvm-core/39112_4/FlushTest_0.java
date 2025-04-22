        Observable.from(keys).flatMap(new Func1<String, Observable<UpsertResponse>>() {
            @Override
            public Observable<UpsertResponse> call(String key) {
                return cluster().send(new UpsertRequest(key, Unpooled.copiedBuffer("Content", CharsetUtil.UTF_8), bucket()));
            }
        }).toBlocking().last();
