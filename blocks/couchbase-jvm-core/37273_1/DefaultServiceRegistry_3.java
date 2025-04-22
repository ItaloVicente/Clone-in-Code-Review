    public List<Service> services() {
        return serviceCache;
    }

    private void recalculateServiceCache() {
        List<Service> services = new ArrayList<Service>();
        for (Service service : globalServices.values()) {
            services.add(service);
        }
        for (Map<ServiceType, Service> bucket : localServices.values()) {
            for (Service service : bucket.values()) {
                services.add(service);
