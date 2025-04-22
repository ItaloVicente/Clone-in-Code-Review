	private final String name;

	/**
	 * Instantiate the abstract logger.
	 */
	protected AbstractLogger(String nm) {
		super();
		if(nm == null) {
			throw new NullPointerException("Logger name may not be null.");
		}
		name=nm;
	}

	/**
	 * Get the name of this logger.
	 */
	public String getName() {
		return(name);
	}

    /**
     * Get the throwable from the last element of this array if it is
     * Throwable, else null.
     */
    public Throwable getThrowable(Object args[]) {
        Throwable rv=null;
        if(args.length > 0) {
            if(args[args.length-1] instanceof Throwable) {
                rv=(Throwable)args[args.length-1];
            }
        }
        return rv;
    }
