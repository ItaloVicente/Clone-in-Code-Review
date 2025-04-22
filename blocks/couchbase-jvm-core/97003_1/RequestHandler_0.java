                        if (alternateNetwork != null) {
                            AlternateAddress aa = nodeInfo.alternateAddresses().get(alternateNetwork);
                            Map<ServiceType, Integer> services =
                                environment.sslEnabled() ? aa.sslServices() : aa.services();
                            return Observable.just(services);
                        } else {
                            Map<ServiceType, Integer> services =
