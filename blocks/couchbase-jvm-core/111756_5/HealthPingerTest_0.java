        PingReport pr;
        ServiceType[] servicesToPing = useMock()
            ? new ServiceType[]{ServiceType.BINARY, ServiceType.VIEW} // mock only supports these right now
            : new ServiceType[0]; // empty means all services

        pr = HealthPinger.ping(
                env(),
                bucket(),
                password(),
                cluster(),
                "ping-id",
                1,
                TimeUnit.SECONDS,
                servicesToPing
            ).toBlocking().value();
