        Observable
                .from(keys)
                .flatMap(new Func1<String, Observable<UpsertResponse>>() {
                    @Override
                    public Observable<UpsertResponse> call(String key) {
                        final CoreDocument document = new CoreDocument(key, Unpooled.copiedBuffer("Content", CharsetUtil.UTF_8), 0, 0, 0, false, null);
                        return cluster().send(new UpsertRequest(document, bucket()));
                    }
                })
                .toBlocking()
                .last();
