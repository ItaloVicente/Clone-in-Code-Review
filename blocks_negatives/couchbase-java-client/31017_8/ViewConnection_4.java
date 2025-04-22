  public ViewConnection(CouchbaseConnectionFactory cf,
      List<InetSocketAddress> addrs, Collection<ConnectionObserver> obs)
    throws IOException {
    connFactory = cf;
    connObservers.addAll(obs);
    couchNodes = createConnections(addrs);
    nextNode = 0;
  }
