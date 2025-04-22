
    static class WriteLogListener implements GenericFutureListener<Future<Void>> {

        @Override
        public void operationComplete(Future<Void> future) throws Exception {
            if (!future.isSuccess()) {
                LOGGER.warn("Error during IO write phase.", future);
            }
        }

    }

