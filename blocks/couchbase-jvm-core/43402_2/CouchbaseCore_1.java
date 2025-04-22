        requestDisruptor.handleExceptionsWith(new ExceptionHandler() {
            @Override
            public void handleEventException(Throwable ex, long sequence, Object event) {
                LOGGER.warn("Exception while Handling Request Events {}, {}", event, ex);
            }

            @Override
            public void handleOnStartException(Throwable ex) {
                LOGGER.warn("Exception while Starting Request RingBuffer {}", ex);
            }

            @Override
            public void handleOnShutdownException(Throwable ex) {
                LOGGER.info("Exception while shutting down Request RingBuffer {}", ex);
            }
        });
