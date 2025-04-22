		super(parentShell);
		setShellStyle(getShellStyle() & ~SWT.APPLICATION_MODAL | SWT.RESIZE);
		setBlockOnOpen(false);
		this.localDb = localDb;
		this.result = new FetchOperationResult(result.getURI(), result);
		this.sourceString = sourceString;
		fetchResultImage = UIIcons.WIZBAN_FETCH.createImage();
