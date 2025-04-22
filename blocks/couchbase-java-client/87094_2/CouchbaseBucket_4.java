
    @Override
    public PingReport ping(String reportId, long timeout, TimeUnit timeUnit) {
        return asyncBucket.ping(reportId, timeout, timeUnit).toBlocking().value();
    }

    @Override
    public PingReport ping(long timeout, TimeUnit timeUnit) {
        return asyncBucket.ping(timeout, timeUnit).toBlocking().value();
    }

    @Override
    public PingReport ping(List<ServiceType> services, long timeout, TimeUnit timeUnit) {
        return asyncBucket.ping(services, timeout, timeUnit).toBlocking().value();
    }

    @Override
    public PingReport ping(String reportId, List<ServiceType> services, long timeout, TimeUnit timeUnit) {
        return asyncBucket.ping(reportId, services, timeout, timeUnit).toBlocking().value();
    }

    @Override
    public PingReport ping(String reportId) {
        return ping(reportId, environment.managementTimeout(), TIMEOUT_UNIT);
    }

    @Override
    public PingReport ping() {
        return ping(environment.managementTimeout(), TIMEOUT_UNIT);
    }

    @Override
    public PingReport ping(List<ServiceType> services) {
        return ping(services, environment.managementTimeout(), TIMEOUT_UNIT);
    }

    @Override
    public PingReport ping(String reportId, List<ServiceType> services) {
        return ping(reportId, services, environment.managementTimeout(), TIMEOUT_UNIT);
    }
