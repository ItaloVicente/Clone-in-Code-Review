  public CouchbaseConnectionFactoryBuilder setViewWorkerSize(int workers) {
    if (workers < 1) {
      throw new IllegalArgumentException("The View worker size needs to be "
        + "greater than zero.");
    }

    viewWorkers = workers;
    return this;
  }

  public CouchbaseConnectionFactoryBuilder setViewConnsPerNode(int conns) {
    if (conns < 1) {
      throw new IllegalArgumentException("The View connections per node need "
        + "to be greater than zero");
    }
    viewConns = conns;
    return this;
  }

