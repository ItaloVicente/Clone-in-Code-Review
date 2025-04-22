      if(connectingNodes.contains(sa)) {
        getLogger().debug("Suppressing connection creation for node "
          + sa.toString() + "while still connecting");
        continue;
      }
      connectingNodes.add(sa);

      MemcachedNode qa = null;
