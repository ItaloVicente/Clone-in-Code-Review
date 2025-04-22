        if (ioPoolSize < MIN_POOL_SIZE) {
            LOGGER.info("ioPoolSize is less than {} ({}), setting to: {}", MIN_POOL_SIZE, ioPoolSize, MIN_POOL_SIZE);
            this.ioPoolSize = MIN_POOL_SIZE;
        } else {
            this.ioPoolSize = ioPoolSize;
        }

        if (computationPoolSize < MIN_POOL_SIZE) {
            LOGGER.info("computationPoolSize is less than {} ({}), setting to: {}", MIN_POOL_SIZE, computationPoolSize,
                MIN_POOL_SIZE);
            this.computationPoolSize = MIN_POOL_SIZE;
        } else {
            this.computationPoolSize = computationPoolSize;
        }

