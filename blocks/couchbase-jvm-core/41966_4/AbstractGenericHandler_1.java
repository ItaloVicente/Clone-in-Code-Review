            publishResponse(response, currentRequest.observable());
        }

        if (currentDecodingState == DecodingState.FINISHED) {
            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace(logIdent(ctx, endpoint) + "Finished decoding of " + currentRequest);
