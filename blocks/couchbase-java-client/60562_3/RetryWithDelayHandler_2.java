            final long delay = retryDelay.calculate(errorNumber);
            final TimeUnit unit = retryDelay.unit();

            if (doOnRetry != null) {
                doOnRetry.call(errorNumber, error, delay, unit);
            }

