
                                if (obs.size() == 1) {
                                    return obs.get(0);
                                } else {
                                    return Observable.mergeDelayError(Observable.from(obs));
                                }
