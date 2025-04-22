    @Override
    public Observable<Boolean> disconnect() {
        return core
            .<DisconnectResponse>send(new DisconnectRequest())
            .map(new Func1<DisconnectResponse, Boolean>() {
                     @Override
                     public Boolean call(DisconnectResponse response) {
                         return response.status() == ResponseStatus.SUCCESS;
                     }
                 }
            );
    }
