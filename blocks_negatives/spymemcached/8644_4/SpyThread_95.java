	private transient Logger logger=null;


	/**
	 * Get an instance of SpyThread.
	 */
	public SpyThread() {
		super();
	}

	/**
	 * Get an instance of SpyThread with a name.
	 *
	 * @param name thread name
	 */
	public SpyThread(String name) {
		super(name);
	}

	/**
	 * Get a Logger instance for this class.
	 *
	 * @return an appropriate logger instance.
	 */
	protected Logger getLogger() {
		if(logger==null) {
			logger=LoggerFactory.getLogger(getClass());
		}
		return(logger);
	}
