    synchronized (monitoredFutures) {
      Iterator it = monitoredFutures.iterator();
      while (it.hasNext()) {
        GetFuture future = (GetFuture) it.next();
        if (!future.cancel(ign)) {
          allCancelled = false;
        }
