                            if (shouldAddService(service.getKey(), nodeInfo.hostname(), config)) {
                                requests.add(addService(new AddServiceRequest(
                                    service.getKey(),
                                    config.name(),
                                    config.username(),
                                    config.password(),
                                    service.getValue(),
                                    nodeInfo.hostname()
                                )));
                            }
                        }

                        if (config instanceof CouchbaseBucketConfig) {
                            for (Node node : nodes) {
                                if (node.hostname().equals(nodeInfo.hostname())) {
                                    if (node.serviceEnabled(ServiceType.BINARY) &&
                                        !((CouchbaseBucketConfig) config).hasPrimaryPartitionsOnNode(nodeInfo.hostname())) {
                                        requests.add(removeService(new RemoveServiceRequest(
                                            ServiceType.BINARY,
                                            config.name(),
                                            nodeInfo.hostname())
                                        ));
                                        LOGGER.debug("Node {} doesn't have active partitions anymore, removing service.", nodeInfo.hostname());
                                    }
                                    break;
                                }
                            }
