    @Override
    public Observable<Boolean> exists(String id) {
        return core
            .<ObserveResponse>send(new ObserveRequest(id, 0, true, (short) 0, bucket))
            .map(new Func1<ObserveResponse, Boolean>() {
                @Override
                public Boolean call(ObserveResponse response) {
                    ByteBuf content = response.content();
                    if (content != null && content.refCnt() > 0) {
                        content.release();
                    }

                    ObserveResponse.ObserveStatus foundStatus = response.observeStatus();
                    if (foundStatus == ObserveResponse.ObserveStatus.FOUND_PERSISTED
                        || foundStatus == ObserveResponse.ObserveStatus.FOUND_NOT_PERSISTED) {
                        return true;
                    }

                    return false;
                }
            });
    }

    @Override
    public <D extends Document<?>> Observable<Boolean> exists(D document) {
        return exists(document.id());
    }

