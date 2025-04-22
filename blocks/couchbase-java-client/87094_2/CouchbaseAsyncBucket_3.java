    @Override
    public Single<PingReport> ping(String reportId, long timeout, TimeUnit timeUnit) {
        return HealthPinger.ping(environment, bucket, password, core, reportId, timeout, timeUnit);
    }

    @Override
    public Single<PingReport> ping(long timeout, TimeUnit timeUnit) {
        return HealthPinger.ping(environment, bucket, password, core, null, timeout, timeUnit);
    }

    @Override
    public Single<PingReport> ping(List<ServiceType> services, long timeout, TimeUnit timeUnit) {
        return HealthPinger.ping(environment, bucket, password, core, null, timeout, timeUnit,
          services.toArray(new ServiceType[services.size()]));
    }

    @Override
    public Single<PingReport> ping(String reportId, List<ServiceType> services, long timeout, TimeUnit timeUnit) {
        return HealthPinger.ping(environment, bucket, password, core, reportId, timeout, timeUnit,
          services.toArray(new ServiceType[services.size()]));
    }

