                                    for (short i = 1; i <= replicas; i++) {
                                        Observable<ObserveResponse> res = core.send(new ObserveRequest(id, cas, false, i, bucket));
                                        if (swallowErrors) {
                                            obs.add(res.onErrorResumeNext(Observable.<ObserveResponse>empty()));
                                        } else {
                                            obs.add(res);
                                        }
