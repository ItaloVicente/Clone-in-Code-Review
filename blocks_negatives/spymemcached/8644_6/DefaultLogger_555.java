	private final SimpleDateFormat df;

	/**
	 * Get an instance of DefaultLogger.
	 */
	public DefaultLogger(String name) {
		super(name);
		df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	}

	/**
	 * False.
	 */
	@Override
	public boolean isDebugEnabled() {
		return(false);
	}

	/**
	 * True.
	 */
	@Override
	public boolean isInfoEnabled() {
		return(true);
	}

	/**
	 * @see AbstractLogger
	 */
	@Override
	public synchronized void log(Level level, Object message, Throwable e) {
		if(level == Level.INFO
			|| level == Level.WARN
			|| level == Level.ERROR
			|| level == Level.FATAL) {
			System.err.printf("%s %s %s:  %s\n",
					df.format(new Date()), level.name(), getName(), message);
			if(e != null) {
				e.printStackTrace();
			}
		}
	}
