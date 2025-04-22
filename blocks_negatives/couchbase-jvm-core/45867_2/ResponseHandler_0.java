                switch (status) {
                    case SUCCESS:
                    case EXISTS:
                    case NOT_EXISTS:
                    case FAILURE:
                        final Scheduler.Worker worker = environment.scheduler().createWorker();
                        final Subject<CouchbaseResponse, CouchbaseResponse> obs = event.getObservable();
                        worker.schedule(new Action0() {
                            @Override
                            public void call() {
                                try {
                                    obs.onNext(response);
                                    obs.onCompleted();
                                } catch(Exception ex) {
                                    obs.onError(ex);
                                } finally {
                                    worker.unsubscribe();
                                }
