	public ResetPerspectiveAction(IWorkbenchWindow window) {
		super(window);
		setText(WorkbenchMessages.ResetPerspective_text);
		setActionDefinitionId(IWorkbenchCommandConstants.WINDOW_RESET_PERSPECTIVE);
		setToolTipText(WorkbenchMessages.ResetPerspective_toolTip);
		window.getWorkbench().getHelpSystem().setHelp(this, IWorkbenchHelpContextIds.RESET_PERSPECTIVE_ACTION);
	}
