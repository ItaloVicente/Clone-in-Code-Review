            try {
                streamingConfigObservable.onCompleted();
            } catch (RejectedExecutionException ex) {
                LOGGER.info(logIdent(ctx, endpoint()) + "Could not complete config stream, scheduler shut "
                    + "down already.");
            }
