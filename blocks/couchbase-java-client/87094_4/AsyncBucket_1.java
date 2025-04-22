

    Single<PingReport> ping(String reportId, long timeout, TimeUnit timeUnit);

    Single<PingReport> ping(long timeout, TimeUnit timeUnit);

    Single<PingReport> ping(Collection<ServiceType> services, long timeout, TimeUnit timeUnit);

    Single<PingReport> ping(String reportId, Collection<ServiceType> services, long timeout, TimeUnit timeUnit);

