    try {
      synchronized (viewNodes) {
        Iterator<HttpHost> iter = viewNodes.iterator();
        while (iter.hasNext()) {
          HttpHost current = iter.next();
          if (!newServerAddresses.contains(current.getAddress())) {
            iter.remove();
