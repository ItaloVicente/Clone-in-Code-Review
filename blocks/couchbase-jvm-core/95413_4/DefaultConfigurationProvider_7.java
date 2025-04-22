        if (oldConfig == null) {
            switch (environment.networkResolution()) {
                case DEFAULT:
                    externalNetwork = false;
                    break;
                case EXTERNAL:
                    externalNetwork = true;
                    break;
                case AUTOMATIC:
                    externalNetwork = determineNetworkResolution(newConfig);
                    break;
            }
            LOGGER.info("Selected network configuration: {}", externalNetwork ? "external" : "default");
        }
        newConfig.useAlternateNetwork(externalNetwork);

