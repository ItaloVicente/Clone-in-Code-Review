  private static LoggerFactory instance = null;

  private final ConcurrentMap<String, Logger> instances;
  private Constructor<? extends Logger> instanceConstructor;

  private LoggerFactory() {
    super();
    instances = new ConcurrentHashMap<String, Logger>();
  }

  private static void init() {
    if (instance == null) {
      instance = new LoggerFactory();
    }
  }

  public static Logger getLogger(Class<?> clazz) {
    return (getLogger(clazz.getName()));
  }

  public static Logger getLogger(String name) {
    if (name == null) {
      throw new NullPointerException("Logger name may not be null.");
    }
    init();
    return (instance.internalGetLogger(name));
  }

  private Logger internalGetLogger(String name) {
    assert name != null : "Name was null";
    Logger rv = instances.get(name);

    if (rv == null) {
      Logger newLogger = null;
      try {
        newLogger = getNewInstance(name);
      } catch (Exception e) {
        throw new RuntimeException("Problem getting logger", e);
      }
      Logger tmp = instances.putIfAbsent(name, newLogger);
      rv = tmp == null ? newLogger : tmp;
    }

    return (rv);
  }

  private Logger getNewInstance(String name) throws InstantiationException,
      IllegalAccessException, InvocationTargetException {

    if (instanceConstructor == null) {
      getConstructor();
    }
    Object[] args = { name };
    Logger rv = instanceConstructor.newInstance(args);

    return (rv);
  }

  @SuppressWarnings("unchecked")
  private void getConstructor() {
    Class<? extends Logger> c = DefaultLogger.class;
    String className = System.getProperty("net.spy.log.LoggerImpl");

    if (className != null) {
      try {
        c = (Class<? extends Logger>) Class.forName(className);
      } catch (NoClassDefFoundError e) {
        System.err.println("Warning:  " + className
            + " not found while initializing"
            + " net.spy.compat.log.LoggerFactory");
        e.printStackTrace();
        c = DefaultLogger.class;
      } catch (ClassNotFoundException e) {
        System.err.println("Warning:  " + className
            + " not found while initializing"
            + " net.spy.compat.log.LoggerFactory");
        e.printStackTrace();
        c = DefaultLogger.class;
      }
    }

    try {
      Class<?>[] args = { String.class };
      instanceConstructor = c.getConstructor(args);
    } catch (NoSuchMethodException e) {
      try {
        Class<?>[] args = {};
        instanceConstructor = c.getConstructor(args);
      } catch (NoSuchMethodException e2) {
        System.err.println("Warning:  " + className
            + " has no appropriate constructor, using defaults.");

        try {
          Class<?>[] args = { String.class };
          instanceConstructor = DefaultLogger.class.getConstructor(args);
        } catch (NoSuchMethodException e3) {
          throw new NoSuchMethodError("There used to be a constructor that "
              + "takes a single String on " + DefaultLogger.class + ", but I "
              + "can't find one now.");
        } // SOL
      } // No empty constructor
    } // No constructor that takes a string
  } // getConstructor
