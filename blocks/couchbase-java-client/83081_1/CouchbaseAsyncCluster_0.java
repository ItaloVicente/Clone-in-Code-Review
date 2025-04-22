    @Override
    public Observable<ServicesHealth> healthCheck(boolean ping) {
        if (ping) {
            throw new UnsupportedOperationException("ping not yet supported");
        }
        return core.<HealthCheckResponse>send(new HealthCheckRequest()).map(new Func1<HealthCheckResponse, ServicesHealth>() {
            @Override
            public ServicesHealth call(HealthCheckResponse response) {
                return response.servicesHealth();
            }
        });
    }

