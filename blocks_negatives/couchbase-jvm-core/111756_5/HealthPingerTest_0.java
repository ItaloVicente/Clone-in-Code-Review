        PingReport pr = HealthPinger.ping(
            env(),
            bucket(),
            password(),
            cluster(),
            "ping-id",
            1,
            TimeUnit.SECONDS
        ).toBlocking().value();
