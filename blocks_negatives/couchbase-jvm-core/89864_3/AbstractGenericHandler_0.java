                try {
                    observable.onNext(response);
                    observable.onCompleted();
                } catch (Exception ex) {
                    LOGGER.warn("Caught exception while onNext on observable", ex);
                    observable.onError(ex);
                } finally {
                    worker.unsubscribe();
                }
