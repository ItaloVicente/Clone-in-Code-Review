
    @Override
    public PingReport ping(String reportId, long timeout, TimeUnit timeUnit) {
        return asyncBucket.ping(reportId, timeout, timeUnit).toBlocking().single();
    }

    @Override
    public PingReport ping(long timeout, TimeUnit timeUnit) {
        return asyncBucket.ping(timeout, timeUnit).toBlocking().single();
    }

    @Override
    public PingReport ping(List<ServiceType> services, long timeout, TimeUnit timeUnit) {
        return asyncBucket.ping(services, timeout, timeUnit).toBlocking().single();
    }

    @Override
    public PingReport ping(String reportId, List<ServiceType> services, long timeout, TimeUnit timeUnit) {
        return asyncBucket.ping(reportId, services, timeout, timeUnit).toBlocking().single();
    }
