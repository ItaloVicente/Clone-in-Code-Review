  @Override
  public MetricType enableMetrics() {
    String metricType = System.getProperty("net.spy.metrics.type");
    return metricType == null
      ? DEFAULT_METRIC_TYPE : MetricType.valueOf(metricType.toUpperCase());
  }

  @Override
  public MetricCollector getMetricCollector() {
    if (metrics != null) {
      return metrics;
    }

    String enableMetrics = System.getProperty("net.spy.metrics.enable");
    if (enableMetrics().equals(MetricType.OFF) || enableMetrics == "false") {
      getLogger().debug("Metric collection disabled.");
      metrics =  new NoopMetricCollector();
    } else {
      getLogger().info("Metric collection enabled (Profile " + enableMetrics() + ").");
      metrics = new DefaultMetricCollector();
    }

    return metrics;
  }

