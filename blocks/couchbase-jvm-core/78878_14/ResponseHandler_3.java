
        if (request.retryDelay() != null) {
            Delay delay = request.retryDelay();
            if (request.retryCount() == 0) {
                delayTime = request.retryAfter();
                request.incrementRetryCount();
            } else {
                delayTime = delay.calculate(request.incrementRetryCount());
            }
