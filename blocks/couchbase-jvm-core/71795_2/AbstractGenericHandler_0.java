        if (!pipeline && !sentRequestQueue.isEmpty()) {
            if (traceEnabled) {
                LOGGER.trace("Rescheduling {} because pipelining disable and a request is in-flight.", msg);
            }
            RetryHelper.retryOrCancel(env(), (CouchbaseRequest) msg, responseBuffer);
            return;
        }

