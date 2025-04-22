      if (vbnodesMap.containsKey(address)) {
        vbnodesMap.put(address, node);
        getLogger().debug("Adding node with address %s.",
          address);
        getLogger().debug("Node added is %s.", node);
      } else if (vbnodesMap.containsKey(hostname)) {
        vbnodesMap.put(hostname, node);
        getLogger().debug("Adding node with hostname %s.",
          hostname);
        getLogger().debug("Node added is %s.", node);
      }
    }
    for (Map.Entry<String, MemcachedNode> entry : vbnodesMap.entrySet()) {
      if (entry.getValue() == null) {
        getLogger().error("Critical reconfiguration error: " +
            "Server list from Configuration and Nodes " +
            "are out of synch. causing %s to be removed",
                entry.getKey());
        vbnodesMap.remove(entry.getKey());
      }
    }
