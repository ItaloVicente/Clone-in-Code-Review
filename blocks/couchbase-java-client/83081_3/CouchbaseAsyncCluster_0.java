    @Override
    public Observable<ServicesHealth> healthCheck(boolean ping) {
        return core.<HealthCheckResponse>send(new HealthCheckRequest(ping))
            .map(new Func1<HealthCheckResponse, ServicesHealth>() {
                @Override
                public ServicesHealth call(HealthCheckResponse response) {
                    return response.servicesHealth();
                }
            });
    }

