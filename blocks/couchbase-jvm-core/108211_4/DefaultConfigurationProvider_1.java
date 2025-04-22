        LOGGER.debug(
            "Registering bucket {} for refresh at {}",
            config.name(),
            refresher.getClass().getSimpleName()
        );
        refresher.registerBucket(config.name(), config.username(), config.password()).subscribe();
