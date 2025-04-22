      MemcachedNode node = findNode(sa);
      if (node != null) {
          authMonitor.authConnection(mconn, opFact, authDescriptor, node);
      }
      else {
          getLogger().warn("Could not find a connection to node \"" + sa + "\""
            + " to authenticate against.");
      }
