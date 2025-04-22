	public AbstractNotificationPopup(Shell shell) {
		this(shell, SWT.NO_TRIM | SWT.ON_TOP | SWT.NO_FOCUS | SWT.TOOL);
	}

	public AbstractNotificationPopup(Shell parentShell, int style) {
		super(new Shell(parentShell.getDisplay()));
		setShellStyle(style);

		this.parentShell = parentShell;
		this.display = parentShell.getDisplay();
		this.resources = new LocalResourceManager(JFaceResources.getResources());

		this.closeJob.setSystem(true);
	}

