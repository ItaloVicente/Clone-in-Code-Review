        if (!pipeline && (!sentRequestQueue.isEmpty() || currentDecodingState != DecodingState.INITIAL)) {
            if (traceEnabled) {
                LOGGER.trace("Rescheduling {} because pipelining disable and a request is in-flight.", msg);
            }
            RetryHelper.retryOrCancel(env(), (CouchbaseRequest) msg, responseBuffer);
            return;
        }

