        try {
            localId = paddedHex(endpoint.context().coreId()) + "/" + paddedHex(ctx.channel().hashCode());
        } catch (Exception ex) {
            LOGGER.info("Could not define channel ID, ignoring.");
            localId = paddedHex(0) + "/" + paddedHex(0);
        }
