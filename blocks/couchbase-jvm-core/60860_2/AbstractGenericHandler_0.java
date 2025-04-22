            resetStatesAfterDecode(ctx);
        }
    }

    private void writeMetrics(final CouchbaseResponse response) {
        if (currentRequest != null && currentOpTime >= 0 && env() != null
            && env().networkLatencyMetricsCollector().isEnabled()) {

            Class<? extends CouchbaseRequest> requestClass = currentRequest.getClass();
            String simpleName = classNameCache.get(requestClass);
            if (simpleName == null) {
                simpleName = requestClass.getSimpleName();
                classNameCache.put(requestClass, simpleName);
            }

            NetworkLatencyMetricsIdentifier identifier = new NetworkLatencyMetricsIdentifier(
                    remoteHostname,
                    serviceType().toString(),
                    simpleName,
                    response.status().toString()
            );
            env().networkLatencyMetricsCollector().record(identifier, currentOpTime);
        }
    }

    private void resetStatesAfterDecode(final ChannelHandlerContext ctx) {
        if (traceEnabled) {
            LOGGER.trace("{}Finished decoding of {}", logIdent(ctx, endpoint), currentRequest);
        }
        currentRequest = null;
        currentDecodingState = DecodingState.INITIAL;
    }

    private void initialDecodeTasks(final ChannelHandlerContext ctx) {
        currentRequest = sentRequestQueue.poll();
        currentDecodingState = DecodingState.STARTED;

        if (currentRequest != null) {
            Long st = sentRequestTimings.poll();
            if (st != null) {
                currentOpTime = System.nanoTime() - st;
            } else {
                currentOpTime = -1;
