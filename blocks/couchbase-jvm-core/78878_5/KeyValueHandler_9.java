        ErrorMap.ErrorCode errorCode = ResponseStatusConverter.readErrorCodeFromErrorMap(msg.getStatus());

        if (errorCode != null) {
            LOGGER.debug("ResponseStatus with Extended Error Code {}", errorCode.toString());

            if (errorCode.attributes().contains(CONN_STATE_INVALIDATED)) {
                LOGGER.debug(logIdent(ctx, endpoint()) +
                        "Connection state has been invalidated by the server, reconnecting and retrying");
                endpoint().disconnect();
                status = ResponseStatus.RETRY;
            } else if (errorCode.attributes().contains(FETCH_CONFIG)) {
                LOGGER.debug(logIdent(ctx, endpoint()) +
                        "Config reload requested by the server, sending config reload message and retrying");
                endpoint().signalConfigReload();
                status = ResponseStatus.RETRY;
            } else if (errorCode.attributes().contains(AUTO_RETRY) ||
                    errorCode.attributes().contains(RETRY_NOW) ||
                    errorCode.attributes().contains(RETRY_LATER)) {
                LOGGER.debug(logIdent(ctx, endpoint()) +
                        "Retry requested by the server");
                status = ResponseStatus.RETRY;
            }

            if (errorCode.attributes().contains(AUTO_RETRY) && request.retryDelay() == null) {
                if (errorCode.retrySpec().strategy() == CONSTANT) {
                    request.retryDelay(Delay.fixed(errorCode.retrySpec().interval(), TimeUnit.MILLISECONDS));
                } else if (errorCode.retrySpec().strategy() == LINEAR) {
                    request.retryDelay(Delay.linear(TimeUnit.MILLISECONDS, errorCode.retrySpec().ceil(), 0, errorCode.retrySpec().interval()));
                } else if (errorCode.retrySpec().strategy() == EXPONENTIAL) {
                    request.retryDelay(Delay.exponential(TimeUnit.MILLISECONDS, errorCode.retrySpec().ceil(), 0, errorCode.retrySpec().interval()));
                }
                request.retryAfter(errorCode.retrySpec().after());
                request.maxRetryDuration(System.currentTimeMillis() + errorCode.retrySpec().maxDuration());
            }
        }

