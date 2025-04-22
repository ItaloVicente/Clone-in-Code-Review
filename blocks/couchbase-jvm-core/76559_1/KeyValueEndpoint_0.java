            .addLast(new BinaryMemcacheObjectAggregator(Integer.MAX_VALUE));

        if (authBeforeHello) {
            LOGGER.info("Manually enforced authentication before \"HELLO\" for backwards " +
                "compatibility.");
            pipeline
                .addLast(new KeyValueAuthHandler(username(), password()))
                .addLast(new KeyValueFeatureHandler(environment()))
                .addLast(new KeyValueErrorMapHandler());
        } else {
            pipeline
                .addLast(new KeyValueFeatureHandler(environment()))
                .addLast(new KeyValueErrorMapHandler())
                .addLast(new KeyValueAuthHandler(username(), password()));
        }

        pipeline
