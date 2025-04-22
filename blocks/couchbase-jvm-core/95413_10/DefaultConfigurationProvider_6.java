        if (oldConfig == null) {
            externalNetwork = determineNetworkResolution(newConfig, environment.networkResolution(), seedHosts);
            LOGGER.info("Selected network configuration: {}", externalNetwork != null ? externalNetwork : "default");
        }

        if (externalNetwork != null) {
            newConfig.useAlternateNetwork(environment.networkResolution().name());
        }

