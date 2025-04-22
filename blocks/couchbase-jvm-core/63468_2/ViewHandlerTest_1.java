    private int countAndRelease(Observable<ByteBuf> bufObservable) {
        return bufObservable
                .doOnNext(new Action1<ByteBuf>() {
                    @Override
                    public void call(ByteBuf byteBuf) {
                        byteBuf.release();
                    }
                }).count()
                .toBlocking()
                .singleOrDefault(0);
    }

