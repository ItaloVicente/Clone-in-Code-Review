            if (connectionString.hosts().size() == 1) {
                InetSocketAddress lookupNode = connectionString.hosts().get(0);
                LOGGER.debug("Attempting to load DNS SRV records from {}.", connectionString.hosts().get(0));
                try {
                    List<String> foundNodes = Bootstrap.fromDnsSrv(lookupNode.getHostName(), false,
                        environment.sslEnabled());
                    if (foundNodes.isEmpty()) {
                        throw new IllegalStateException("DNS SRV list is empty.");
                    }
                    seedNodes.addAll(foundNodes);
                    LOGGER.info("Loaded seed nodes from DNS SRV {}.", foundNodes);
                } catch (Exception ex) {
                    LOGGER.warn("DNS SRV lookup failed, proceeding with normal bootstrap.", ex);
                    seedNodes.add(lookupNode.getHostName());
                }
            } else {
                LOGGER.info("DNS SRV enabled, but less or more than one seed node given. Proceeding with normal "
                    + "bootstrap.");
                for (InetSocketAddress node : connectionString.hosts()) {
                    seedNodes.add(node.getHostName());
                }
            }
