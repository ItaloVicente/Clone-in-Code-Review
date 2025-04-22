    @Override
    public List<InetAddress> nodeList() {
        List<InetAddress> nodes = new ArrayList<InetAddress>();
        for (Object node : raw.getArray("nodes")) {
            try {
                String hostname = ((JsonObject) node).getString("hostname");
                String[] hostAndPort = hostname.split(":");
                nodes.add(InetAddress.getByName(hostAndPort[0]));
            } catch (Exception ex) {
                LOGGER.warn("Exception while parsing node list on bucket info.", ex);
            }
        }
        return nodes;
    }

