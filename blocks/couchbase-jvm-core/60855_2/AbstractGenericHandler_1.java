    private static void scheduleDirect(CoreScheduler scheduler, final CouchbaseResponse response,
        final Subject<CouchbaseResponse, CouchbaseResponse> observable) {
        scheduler.scheduleDirect(new Action0() {
            @Override
            public void call() {
                try {
                    observable.onNext(response);
                    observable.onCompleted();
                } catch (Exception ex) {
                    LOGGER.warn("Caught exception while onNext on observable", ex);
                    observable.onError(ex);
                }
            }
        });
    }

    private static void scheduleWorker(Scheduler scheduler, final CouchbaseResponse response,
        final Subject<CouchbaseResponse, CouchbaseResponse> observable) {
        final Scheduler.Worker worker = scheduler.createWorker();
        worker.schedule(new Action0() {
            @Override
            public void call() {
                try {
                    observable.onNext(response);
                    observable.onCompleted();
                } catch (Exception ex) {
                    LOGGER.warn("Caught exception while onNext on observable", ex);
                    observable.onError(ex);
                } finally {
                    worker.unsubscribe();
                }
            }
        });
    }

