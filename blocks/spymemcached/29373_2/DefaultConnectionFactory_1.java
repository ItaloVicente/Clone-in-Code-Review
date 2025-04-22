    if (executorService == null) {
      executorService = new ThreadPoolExecutor(
        0,
        Runtime.getRuntime().availableProcessors(),
        60L,
        TimeUnit.SECONDS,
        new SynchronousQueue<Runnable>()
      );
    }

    return executorService;
  }

  @Override
  public boolean isDefaultExecutorService() {
    return true;
