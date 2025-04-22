        if (response.status() != ResponseStatus.RETRY && observable != null) {
            final Scheduler.Worker worker = env().scheduler().createWorker();
            worker.schedule(new Action0() {
                @Override
                public void call() {
                    try {
                        observable.onNext(response);
                        observable.onCompleted();
                    } catch(Exception ex) {
                        LOGGER.warn("Caught exception while onNext on observable", ex);
                        observable.onError(ex);
                    } finally {
                        worker.unsubscribe();
                    }
                }
            });
        } else {
            responseBuffer.publishEvent(ResponseHandler.RESPONSE_TRANSLATOR, response, observable);
        }
