                        Map<ServiceType, Integer> services;
                        if (alternate != null) {
                            AlternateAddress aa = nodeInfo.alternateAddresses().get(alternate);
                            services = environment.sslEnabled() ? aa.sslServices() : aa.services();
                        } else {
                            services = environment.sslEnabled() ? nodeInfo.sslServices() : nodeInfo.services();
                        }
