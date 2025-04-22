    /**
     * A generic future listener which logs unsuccessful writes.
     *
     * Note that {@link ClosedChannelException}s are ignored because they are handled
     * gracefully by the {@link AbstractGenericHandler}.
     */
    static class WriteLogListener implements GenericFutureListener<Future<Void>> {

        @Override
        public void operationComplete(Future<Void> future) throws Exception {
            if (!future.isSuccess() &&
                !(future.cause() instanceof ClosedChannelException) &&
                !(future.cause() instanceof IOException)) {
                LOGGER.warn("Error during IO write phase.", future.cause());
            }
        }

    }

