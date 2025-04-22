        long delayTime = delay.calculate(request.incrementRetryCount());
        TimeUnit delayUnit = delay.unit();

        if (traceLoggingEnabled) {
            LOGGER.trace("Retrying {} with a delay of {} {}", request, delayTime, delayUnit);
        }

