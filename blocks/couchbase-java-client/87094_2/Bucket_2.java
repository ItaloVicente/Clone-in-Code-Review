    PingReport ping(String reportId);

    PingReport ping(String reportId, long timeout, TimeUnit timeUnit);

    PingReport ping();

    PingReport ping(long timeout, TimeUnit timeUnit);

    PingReport ping(List<ServiceType> services);

    PingReport ping(List<ServiceType> services, long timeout, TimeUnit timeUnit);

    PingReport ping(String reportId, List<ServiceType> services);

    PingReport ping(String reportId, List<ServiceType> services, long timeout, TimeUnit timeUnit);


