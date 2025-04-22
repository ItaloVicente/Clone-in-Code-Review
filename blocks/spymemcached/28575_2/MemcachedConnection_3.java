  protected void registerMetrics() {
    if (metricType.equals(MetricType.DEBUG) || metricType.equals(MetricType.PERFORMANCE)) {
      metrics.addHistogram(OVERALL_AVG_BYTES_READ_METRIC);
      metrics.addHistogram(OVERALL_AVG_BYTES_WRITE_METRIC);
      metrics.addHistogram(OVERALL_AVG_TIME_ON_WIRE_METRIC);
      metrics.addMeter(OVERALL_RESPONSE_METRIC);
      metrics.addMeter(OVERALL_REQUEST_METRIC);

      if (metricType.equals(MetricType.DEBUG)) {
        metrics.addCounter(RECON_QUEUE_METRIC);
        metrics.addCounter(SHUTD_QUEUE_METRIC);
        metrics.addMeter(OVERALL_RESPONSE_RETRY_METRIC);
        metrics.addMeter(OVERALL_RESPONSE_SUCC_METRIC);
        metrics.addMeter(OVERALL_RESPONSE_FAIL_METRIC);
      }
    }
  }

