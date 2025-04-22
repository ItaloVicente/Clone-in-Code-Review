    static List<InetSocketAddress> tryResolveHosts(final List<InetSocketAddress> hosts) {
        List<InetSocketAddress> resolvableHosts = new ArrayList<InetSocketAddress>();
        for (InetSocketAddress node : hosts) {
            try {
                node.getAddress().getHostAddress();
                resolvableHosts.add(node);
            } catch (NullPointerException ex) {
                LOGGER.error("Unable to resolve address " + node.toString());
            }
        }
        return resolvableHosts;
    }

