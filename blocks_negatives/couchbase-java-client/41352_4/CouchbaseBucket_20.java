    public Observable<Boolean> unlock(String id, long cas) {
        return core
            .<UnlockResponse>send(new UnlockRequest(id, cas, bucket))
            .map(new Func1<UnlockResponse, Boolean>() {
                @Override
                public Boolean call(UnlockResponse response) {
                    if (response.status() == ResponseStatus.NOT_EXISTS) {
                        throw new DocumentDoesNotExistException();
                    }
                    if (response.status() == ResponseStatus.FAILURE) {
                        throw new CASMismatchException();
                    }
                    return response.status().isSuccess();
                }
            });
