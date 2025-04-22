    synchronized (monitoredFutures) {
      Iterator it = monitoredFutures.iterator();
      while (it.hasNext()) {
        GetFuture future = (GetFuture) it.next();
        if (!future.equals(successFuture)) {
          future.cancel(true);
        }
