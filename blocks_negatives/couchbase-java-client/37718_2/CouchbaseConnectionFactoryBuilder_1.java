  private static final Logger LOGGER =
    Logger.getLogger(CouchbaseConnectionFactoryBuilder.class.getName());
  protected MetricType metricType = null;
  protected MetricCollector collector = null;
  protected ExecutorService executorService = null;
  protected long authWaitTime = -1;
