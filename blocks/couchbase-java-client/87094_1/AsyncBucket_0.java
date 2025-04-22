

    Observable<PingReport> ping(String reportId, long timeout, TimeUnit timeUnit);

    Observable<PingReport> ping(long timeout, TimeUnit timeUnit);

    Observable<PingReport> ping(List<ServiceType> services, long timeout, TimeUnit timeUnit);

    Observable<PingReport> ping(String reportId, List<ServiceType> services, long timeout, TimeUnit timeUnit);

