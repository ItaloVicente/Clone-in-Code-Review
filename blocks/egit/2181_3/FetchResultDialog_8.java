	public FetchResultDialog(final Shell parentShell, final Repository localDb,
			final FetchResult result, final String sourceString) {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE);
		this.localDb = localDb;
		this.result = new FetchOperationResult(result.getURI(), result);
		this.sourceString = sourceString;
