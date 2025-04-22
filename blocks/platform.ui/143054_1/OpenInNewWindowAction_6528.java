	public OpenInNewWindowAction(IWorkbenchWindow window, IAdaptable input) {
		super(WorkbenchMessages.OpenInNewWindowAction_text);
		if (window == null) {
			throw new IllegalArgumentException();
		}
		this.workbenchWindow = window;
		setToolTipText(WorkbenchMessages.OpenInNewWindowAction_toolTip);
		pageInput = input;
		window.getWorkbench().getHelpSystem().setHelp(this, IWorkbenchHelpContextIds.OPEN_NEW_WINDOW_ACTION);
	}
