        if (newConfig.password() == null && oldConfig != null) {
            newConfig.password(oldConfig.password());
        }

        cluster.setBucketConfig(newConfig.name(), newConfig);
        LOGGER.debug("Applying new configuration {}", newConfig);

