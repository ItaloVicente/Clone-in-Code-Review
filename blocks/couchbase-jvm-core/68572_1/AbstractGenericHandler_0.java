        if (sentRequestQueue.size() < sentQueueLimit) {
            ENCODED request = encodeRequest(ctx, msg);
            sentRequestQueue.offer(msg);
            out.add(request);
            sentRequestTimings.offer(System.nanoTime());
        } else {
            LOGGER.warn("Rescheduling {} because sentRequestQueueLimit reached.", msg);
            RetryHelper.retryOrCancel(env(), msg, responseBuffer);
        }
