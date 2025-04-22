  @Override
  public ConnectionFactoryBuilder setEnableMetrics(MetricType type) {
    metricType = type;
    return this;
  }

  @Override
  public ConnectionFactoryBuilder setMetricCollector(MetricCollector collector) {
    this.collector = collector;
    return this;
  }

  @Override
  public ConnectionFactoryBuilder setListenerExecutorService(ExecutorService executorService) {
    this.executorService = executorService;
    return this;
  }

