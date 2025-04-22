    public Observable<HealthCheckResponse> healthCheck() {
        List<Observable<EndpointHealth>> healthChecks = new ArrayList<Observable<EndpointHealth>>(nodes.size());
        for (Node node : nodes) {
            healthChecks.add(node.healthCheck());
        }
        return Observable.merge(healthChecks).toList().map(new Func1<List<EndpointHealth>, HealthCheckResponse>() {
            @Override
            public HealthCheckResponse call(List<EndpointHealth> checks) {
                return new HealthCheckResponse(new ServicesHealth(checks));
            }
        });
    }

