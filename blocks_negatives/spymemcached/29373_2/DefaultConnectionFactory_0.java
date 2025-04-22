  /**
   * The ExecutorService in which the listener callbacks will be executed.
   */
  public static final ExecutorService DEFAULT_LISTENER_EXECUTOR_SERVICE =
    Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

