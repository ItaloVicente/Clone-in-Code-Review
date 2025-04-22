        return core.<UnlockResponse>send(new UnlockRequest(id, cas, bucket)).map(new Func1<UnlockResponse, Boolean>() {
            @Override
            public Boolean call(UnlockResponse unlockResponse) {
                return unlockResponse.status().isSuccess();
            }
        });
