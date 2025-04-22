    @SuppressWarnings("unchecked")
    public <D extends Document<?>> Observable<D> getAndLock(final String id, final int lockTime, final Class<D> target) {
        return core.<GetResponse>send(new GetRequest(id, bucket, true, false, lockTime))
            .filter(new Func1<GetResponse, Boolean>() {
                @Override
                public Boolean call(GetResponse getResponse) {
                    return getResponse.status() == ResponseStatus.SUCCESS;
                }
            })
            .map(new Func1<GetResponse, D>() {
                @Override
                public D call(final GetResponse response) {
                    Transcoder<?, Object> transcoder = (Transcoder<?, Object>) transcoders.get(target);
                    return (D) transcoder.decode(id, response.content(), response.cas(), 0, response.flags(), response.status());
                }
            });
