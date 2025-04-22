    Observable<Boolean> sendAddNodeRequest(final InetSocketAddress address) {
        final NetworkAddress networkAddress = NetworkAddress.create(address.getAddress().getHostAddress());
        return core.<AddNodeResponse>send(new AddNodeRequest(networkAddress))
                .flatMap(new Func1<AddNodeResponse, Observable<AddServiceResponse>>() {
                    @Override
                    public Observable<AddServiceResponse> call(AddNodeResponse addNodeResponse) {
                        if (!addNodeResponse.status().isSuccess()) {
                            throw new CouchbaseException("Could not enable ClusterManager service to function properly.");
                        }
                        int port = environment.sslEnabled() ? environment.bootstrapHttpSslPort() : environment.bootstrapHttpDirectPort();
                        return core.send(new AddServiceRequest(ServiceType.CONFIG, username, password, port, networkAddress));
                    }
                })
                .map(new Func1<AddServiceResponse, Boolean>() {
                    @Override
                    public Boolean call(AddServiceResponse addServiceResponse) {
                        if (!addServiceResponse.status().isSuccess()) {
                            throw new CouchbaseException("Could not enable ClusterManager service to function properly.");
                        }
                        return true;
                    }
                });
    }

