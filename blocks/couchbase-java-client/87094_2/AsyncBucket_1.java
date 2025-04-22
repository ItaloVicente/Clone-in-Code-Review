

    Single<PingReport> ping(String reportId, long timeout, TimeUnit timeUnit);

    Single<PingReport> ping(long timeout, TimeUnit timeUnit);

    Single<PingReport> ping(List<ServiceType> services, long timeout, TimeUnit timeUnit);

    Single<PingReport> ping(String reportId, List<ServiceType> services, long timeout, TimeUnit timeUnit);

