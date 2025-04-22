
    @Override
    public Observable<Boolean> unlock(String id, long cas) {
        return core.<UnlockResponse>send(new UnlockRequest(id, cas, bucket)).map(new Func1<UnlockResponse, Boolean>() {
            @Override
            public Boolean call(UnlockResponse unlockResponse) {
                return unlockResponse.status().isSuccess();
            }
        });
    }

    @Override
    public <D extends Document<?>> Observable<Boolean> unlock(D document) {
        return unlock(document.id(), document.cas());
    }

    @Override
    public Observable<Boolean> touch(String id, int expiry) {
        return core.<TouchResponse>send(new TouchRequest(id, expiry, bucket)).map(new Func1<TouchResponse, Boolean>() {
            @Override
            public Boolean call(TouchResponse touchResponse) {
                return touchResponse.status().isSuccess();
            }
        });
    }

    @Override
    public <D extends Document<?>> Observable<Boolean> touch(D document) {
        return touch(document.id(), document.expiry());
    }
