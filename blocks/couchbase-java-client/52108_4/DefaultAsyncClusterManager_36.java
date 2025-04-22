                                return core.send(new RequestFactory() {
                                    @Override
                                    public CouchbaseRequest call() {
                                        return new AddServiceRequest(ServiceType.CONFIG, username, password,
                                            port, hostname);
                                    }
                                });
