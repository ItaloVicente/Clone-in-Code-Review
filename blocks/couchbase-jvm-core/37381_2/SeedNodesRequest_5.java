        List<InetAddress> parsedNodes = new ArrayList<InetAddress>();
        for (String node : nodes) {
            try {
                parsedNodes.add(InetAddress.getByName(node));
            } catch (UnknownHostException e) {
                throw new CouchbaseException("Unknown host " + node + " in bootstrap list.", e);
            }
        }
        this.nodes = parsedNodes;
