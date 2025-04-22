        } else if (cause instanceof DecoderException && cause.getCause() instanceof SSLHandshakeException) {
            if (!connectFuture.isDone()) {
                connectFuture.setFailure(cause.getCause());
            } else {
                LOGGER.warn(logIdent(ctx, endpoint) + "Caught SSL exception after being connected: "
                    + cause.getMessage(), cause);
            }
