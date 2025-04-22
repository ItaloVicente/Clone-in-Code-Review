	public FetchResultDialog(final Shell parentShell, final Repository localDb,
			final FetchOperationResult result, final String sourceString) {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE);
		this.localDb = localDb;
		this.result = result;
		this.sourceString = sourceString;
	}

