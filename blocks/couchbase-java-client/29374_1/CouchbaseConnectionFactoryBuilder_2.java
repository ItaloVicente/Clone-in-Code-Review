      @Override
      public MetricType enableMetrics() {
        return metricType == null ? super.enableMetrics() : metricType;
      }

      @Override
      public MetricCollector getMetricCollector() {
        return collector == null ? super.getMetricCollector() : collector;
      }

      @Override
      public ExecutorService getListenerExecutorService() {
        System.out.println("called in builder");
        return executorService == null ? super.getListenerExecutorService() : executorService;
      }
