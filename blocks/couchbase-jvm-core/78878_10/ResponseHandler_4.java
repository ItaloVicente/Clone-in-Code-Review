            if (request.maxRetryDuration() != 0 && ((System.currentTimeMillis()) + delayTime) > request.maxRetryDuration()) {
                request.observable().onError(new RequestCancelledException("Could not dispatch request, cancelling "
                        + "instead of retrying as the maximum retry duration specified by Server has been exceeded"));
                return;
            }
        } else {
            Delay delay = env.retryDelay();
            if (isNotMyVbucket) {
                boolean hasFastForward = bucketHasFastForwardMap(request.bucket(), configurationProvider.config());
                delayTime = request.incrementRetryCount() == 0 && hasFastForward ? 0 : nmvbRetryDelay;
                delayUnit = TimeUnit.MILLISECONDS;
            } else {
                delayTime = delay.calculate(request.incrementRetryCount());
                delayUnit = delay.unit();
            }
