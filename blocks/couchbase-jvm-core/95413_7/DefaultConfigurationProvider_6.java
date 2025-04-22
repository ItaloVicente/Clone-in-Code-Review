        if (oldConfig == null) {
            NetworkResolution nr = environment.networkResolution();
            if (nr.equals(NetworkResolution.DEFAULT)) {
                externalNetwork = null;
            } else if (nr.equals(NetworkResolution.AUTOMATIC)) {
                externalNetwork = determineNetworkResolution(newConfig);
            } else {
                externalNetwork = environment.networkResolution().name();
            }
            LOGGER.info("Selected network configuration: {}", externalNetwork != null ? externalNetwork : "default");
        }

        if (externalNetwork != null) {
            newConfig.useAlternateNetwork(environment.networkResolution().name());
        }

