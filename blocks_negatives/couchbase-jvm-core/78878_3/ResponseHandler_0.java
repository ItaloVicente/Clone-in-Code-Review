        if (isNotMyVbucket) {
            boolean hasFastForward = bucketHasFastForwardMap(request.bucket(), configurationProvider.config());
            delayTime = request.incrementRetryCount() == 0 && hasFastForward ? 0 : nmvbRetryDelay;
            delayUnit = TimeUnit.MILLISECONDS;
        } else {
            delayTime = delay.calculate(request.incrementRetryCount());
