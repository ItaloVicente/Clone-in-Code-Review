      if(enableThrottling) {
        for(MemcachedNode node : newNodes) {
          throttleManager.setThrottler(
            (InetSocketAddress)node.getSocketAddress());
        }
        for(MemcachedNode node : oddNodes) {
          throttleManager.removeThrottler(
            (InetSocketAddress)node.getSocketAddress());
        }
      }

