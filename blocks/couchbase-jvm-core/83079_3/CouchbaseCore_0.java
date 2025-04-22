                    .removeService((RemoveServiceRequest) request)
                    .map(new Func1<Service, RemoveServiceResponse>() {
                        @Override
                        public RemoveServiceResponse call(Service service) {
                            return new RemoveServiceResponse(ResponseStatus.SUCCESS);
                        }
                    })
                    .subscribe(request.observable());
        } else if (request instanceof HealthCheckRequest) {
            requestHandler.healthCheck().subscribe(request.observable());
