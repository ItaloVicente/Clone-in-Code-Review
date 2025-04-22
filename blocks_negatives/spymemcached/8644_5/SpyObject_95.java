	private transient Logger logger=null;

	/**
	 * Get an instance of SpyObject.
	 */
	public SpyObject() {
		super();
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

