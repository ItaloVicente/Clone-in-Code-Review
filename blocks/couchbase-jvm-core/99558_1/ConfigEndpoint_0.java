        if (idleTime > 0) {
            pipeline.addLast(new IdleStateHandler(
                idleTime,
                0,
                0,
                TimeUnit.MILLISECONDS)
            );
        }
