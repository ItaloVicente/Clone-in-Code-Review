            requestHandler
                .addService((AddServiceRequest) request)
                .map(new Func1<Service, AddServiceResponse>() {
                    @Override
                    public AddServiceResponse call(Service service) {
                        return new AddServiceResponse(ResponseStatus.SUCCESS, ((AddServiceRequest) request).hostname());
                    }
                })
                .subscribe(request.observable());
