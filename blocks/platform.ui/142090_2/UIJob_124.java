	private Display cachedDisplay;

	public UIJob(String name) {
		super(name);
	}

	public UIJob(Display jobDisplay, String name) {
		this(name);
		setDisplay(jobDisplay);
	}

	public static IStatus errorStatus(Throwable exception) {
		return getStatus(exception);
	}

	@Override
