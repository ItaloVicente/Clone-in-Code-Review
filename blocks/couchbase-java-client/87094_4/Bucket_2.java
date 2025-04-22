    PingReport ping(String reportId);

    PingReport ping(String reportId, long timeout, TimeUnit timeUnit);

    PingReport ping();

    PingReport ping(long timeout, TimeUnit timeUnit);

    PingReport ping(Collection<ServiceType> services);

    PingReport ping(Collection<ServiceType> services, long timeout, TimeUnit timeUnit);

    PingReport ping(String reportId, Collection<ServiceType> services);

    PingReport ping(String reportId, Collection<ServiceType> services, long timeout, TimeUnit timeUnit);


