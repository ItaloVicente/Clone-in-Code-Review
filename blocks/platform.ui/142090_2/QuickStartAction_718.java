	public QuickStartAction(IWorkbenchWindow window) {
		super(IDEWorkbenchMessages.QuickStart_text);
		if (window == null) {
			throw new IllegalArgumentException();
		}
		this.workbenchWindow = window;
		setToolTipText(IDEWorkbenchMessages.QuickStart_toolTip);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
