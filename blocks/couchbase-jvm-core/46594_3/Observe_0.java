                                    Observable<ObserveResponse> res = core.send(new ObserveRequest(id, cas, true, (short) 0, bucket));
                                    if (swallowErrors) {
                                        obs.add(res.onErrorResumeNext(Observable.<ObserveResponse>empty()));
                                    } else {
                                        obs.add(res);
                                    }
