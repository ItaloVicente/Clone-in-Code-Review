  @Override
  public MetricType enableMetrics() {
    return DEFAULT_METRIC_TYPE;
  }

  @Override
  public MetricCollector getMetricCollector() {
    if (metrics != null) {
      return metrics;
    }

    String enableMetrics = System.getProperty("net.spy.metrics.enable", "false");
    if (enableMetrics().equals(MetricType.OFF) || enableMetrics == "false") {
      getLogger().debug("Metric collection disabled.");
      metrics =  new NoopMetricCollector();
    } else {
      getLogger().info("Metric collection enabled (Profile " + enableMetrics() + ").");
      metrics = new DefaultMetricCollector();
    }

    return metrics;
  }

