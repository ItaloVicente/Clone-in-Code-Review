      MemcachedNode node = findNode(sa);
      if (node != null) {
          authMonitor.authConnection(mconn, opFact, authDescriptor, node);
      }
      else {
          getLogger().warn("Unauthenticated Connection established to " +
                  sa + " in cluster " + mconn.getLocator().getAll());
      }
