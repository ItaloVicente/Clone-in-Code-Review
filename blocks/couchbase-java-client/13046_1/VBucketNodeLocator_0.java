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
