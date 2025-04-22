    public Observable<ResponseStatus> addStream(final short partition,
                                                final long vbucketUUID,
                                                final long startSequenceNumber,
                                                final long endSequenceNumber,
                                                final long snapshotStartSequenceNumber,
                                                final long snapshotEndSequenceNumber) {
        if (streams.contains(partition)) {
            return Observable.just(ResponseStatus.EXISTS);
        }
        final DCPConnection connection = this;
        return Observable.defer(new Func0<Observable<StreamRequestResponse>>() {
            @Override
            public Observable<StreamRequestResponse> call() {
                return core.send(new StreamRequestRequest(partition, vbucketUUID, startSequenceNumber,
                        endSequenceNumber, snapshotStartSequenceNumber, snapshotEndSequenceNumber,
                        bucket, password, connection));
            }
        }).flatMap(new Func1<StreamRequestResponse, Observable<StreamRequestResponse>>() {
            @Override
            public Observable<StreamRequestResponse> call(StreamRequestResponse response) {
                long rollbackSequenceNumber;
                switch (response.status()) {
                    case RANGE_ERROR:
                        rollbackSequenceNumber = 0;
                        break;
                    case ROLLBACK:
                        rollbackSequenceNumber = response.rollbackToSequenceNumber();
                        break;
                    default:
                        return Observable.just(response);
                }
                return core.send(new StreamRequestRequest(partition, vbucketUUID, rollbackSequenceNumber,
                        endSequenceNumber, rollbackSequenceNumber, snapshotEndSequenceNumber,
                        bucket, password, connection));
            }
        }).map(new Func1<StreamRequestResponse, ResponseStatus>() {
            @Override
            public ResponseStatus call(StreamRequestResponse response) {
                if (response.status() == ResponseStatus.SUCCESS) {
                    streams.add(partition);
                }
                return response.status();
            }
        });
