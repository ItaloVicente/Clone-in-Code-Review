    public Observable<Service> addService(final AddServiceRequest request) {
        return nodeBy(request.hostname()).addService(request);
    }

    public Observable<Service> removeService(final RemoveServiceRequest request) {
        return nodeBy(request.hostname()).removeService(request);
    }

