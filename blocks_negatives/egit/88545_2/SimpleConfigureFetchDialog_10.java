		super(shell);
		setHelpAvailable(false);
		setShellStyle(getShellStyle() | SWT.SHELL_TRIM);
		this.repository = repository;
		this.config = config;
		this.showBranchInfo = showBranchInfo;

