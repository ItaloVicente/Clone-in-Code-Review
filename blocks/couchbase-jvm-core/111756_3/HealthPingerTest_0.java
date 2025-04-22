        PingReport pr;
        if (useMock()) {
            pr = HealthPinger.ping(
                env(),
                bucket(),
                password(),
                cluster(),
                "ping-id",
                1,
                TimeUnit.SECONDS,
                ServiceType.BINARY,
                ServiceType.VIEW
            ).toBlocking().value();
        } else {
            pr = HealthPinger.ping(
                env(),
                bucket(),
                password(),
                cluster(),
                "ping-id",
                1,
                TimeUnit.SECONDS
            ).toBlocking().value();
        }
