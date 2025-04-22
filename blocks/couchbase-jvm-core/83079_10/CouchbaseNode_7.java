    @Override
    public Observable<EndpointHealth> healthCheck() {
        List<Observable<EndpointHealth>> healthChecks = new ArrayList<Observable<EndpointHealth>>();
        for (Service service : serviceRegistry.services()) {
            healthChecks.add(service.healthCheck());
        }
        return Observable.merge(healthChecks);
    }

