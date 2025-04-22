                                        Observable<StreamRequestResponse> res =
                                                core.send(new StreamRequestRequest(
                                                        partition.shortValue(), feed.vbucketUUID(),
                                                        feed.startSequenceNumber(), feed.endSequenceNumber(),
                                                        feed.snapshotStartSequenceNumber(), feed.snapshotEndSequenceNumber(),
                                                        bucket));
                                        return res.flatMap(new Func1<StreamRequestResponse, Observable<StreamRequestResponse>>() {
                                            @Override
                                            public Observable<StreamRequestResponse> call(StreamRequestResponse response) {
                                                long rollbackSequenceNumber;
                                                switch (response.status()) {
                                                    case RANGE_ERROR:
                                                        rollbackSequenceNumber = 0;
                                                        break;
                                                    case ROLLBACK:
                                                        rollbackSequenceNumber = response.getRollbackToSequenceNumber();
                                                        break;
                                                    default:
                                                        return Observable.just(response);
                                                }
                                                return core.send(new StreamRequestRequest(
                                                        partition.shortValue(), feed.vbucketUUID(),
                                                        rollbackSequenceNumber, feed.endSequenceNumber(),
                                                        feed.snapshotStartSequenceNumber(), feed.snapshotEndSequenceNumber(),
                                                        bucket));
                                            }
                                        });
