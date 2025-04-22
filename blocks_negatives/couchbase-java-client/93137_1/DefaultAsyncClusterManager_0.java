        return Observable
            .just(connectionString.hosts().get(0).getAddress().getHostAddress())
            .map(new Func1<String, NetworkAddress>() {
                @Override
                public NetworkAddress call(String hostname) {
                    return NetworkAddress.create(hostname);
                }
            })
            .flatMap(new Func1<NetworkAddress, Observable<AddServiceResponse>>() {
                @Override
                public Observable<AddServiceResponse> call(final NetworkAddress hostname) {
                    return core
                        .<AddNodeResponse>send(new AddNodeRequest(hostname))
                        .flatMap(new Func1<AddNodeResponse, Observable<AddServiceResponse>>() {
                            @Override
                            public Observable<AddServiceResponse> call(AddNodeResponse response) {
                                int port = environment.sslEnabled()
                                    ? environment.bootstrapHttpSslPort() : environment.bootstrapHttpDirectPort();
                                return core.send(new AddServiceRequest(ServiceType.CONFIG, username, password,
                                    port, hostname));
                            }
                        });
                }
            })
            .map(new Func1<AddServiceResponse, Boolean>() {
                @Override
                public Boolean call(AddServiceResponse addServiceResponse) {
                    if (!addServiceResponse.status().isSuccess()) {
                        throw new CouchbaseException("Could not enable ClusterManager service to function properly.");
