        System.out.println("http loader");
        if (!env().bootstrapHttpEnabled()) {
            LOGGER.info("HTTP Bootstrap disabled, skipping.");
            return Observable.error(new ConfigurationException("Http Bootstrap disabled through configuration."));
        }

