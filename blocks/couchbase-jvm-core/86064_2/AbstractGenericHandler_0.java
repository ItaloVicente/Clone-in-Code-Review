                LOGGER.infoRedacted(
                    "{}Exception thrown while cancelling outstanding operation: {}, {}",
                    meta(logIdent(ctx, endpoint)),
                    user(req.toString()),
                    ex.getMessage()
                );
                LOGGER.debug(logIdent(ctx, endpoint) + "{} Exception", ex);

