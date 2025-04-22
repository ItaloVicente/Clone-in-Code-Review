            String address = addr.getAddress().getHostName() + ":" + addr.getPort();
	    String hostname = addr.getAddress().getHostAddress() + ":" + addr.getPort();
	    getLogger().debug("Adding node with hostname %s and address %s.", hostname, address);
	    getLogger().debug("Node added is %s.", node);
            vbnodesMap.put(address, node);
	    vbnodesMap.put(hostname, node);
