                            NetworkAddress origin = null;
                            if (request != null && request.dispatchHostname() != null) {
                                origin = NetworkAddress.create(request.dispatchHostname());
                            }
                            configurationProvider.proposeBucketConfig(new ProposedBucketConfigContext(
                                response.bucket(),
                                config,
                                origin
                            ));
