    @Override
    public Observable<EndpointHealth> healthCheck() {
        List<Observable<EndpointHealth>> healthChecks = new ArrayList<Observable<EndpointHealth>>();
        for (Endpoint endpoint : endpoints()) {
            healthChecks.add(endpoint.healthCheck(type()).toObservable());
        }
        return Observable.merge(healthChecks);
    }

