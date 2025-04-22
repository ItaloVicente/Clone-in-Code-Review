        Set<InetAddress> nodes = new HashSet<InetAddress>();
        for (NetworkAddress na : clusterNodes) {
            try {
                nodes.add(InetAddress.getByName(na.address()));
            } catch (UnknownHostException e) {
                throw new IllegalStateException(e);
            }

        }
        return nodes;
    }

    public Set<NetworkAddress> clusterNodesAsNetworkAddress() {
