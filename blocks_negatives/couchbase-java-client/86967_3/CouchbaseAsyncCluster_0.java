    public Observable<ServicesHealth> healthCheck() {
        return core.<HealthCheckResponse>send(new HealthCheckRequest())
            .map(new Func1<HealthCheckResponse, ServicesHealth>() {
                @Override
                public ServicesHealth call(HealthCheckResponse response) {
                    return response.servicesHealth();
                }
            });
