  public ConnectionFactoryBuilder setEnableMetrics(MetricType type) {
    metricType = type;
    return this;
  }

  public ConnectionFactoryBuilder setMetricCollector(MetricCollector collector) {
    this.collector = collector;
    return this;
  }

