          if(!inList) {
            getLogger().info("Node configuration is available, but this node "
              + "is part of the current cluster map (i.e. during failover "
              + "but before rebalance)! -> Skipping");
            continue;
          }
