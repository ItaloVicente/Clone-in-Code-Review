	public CloseAllSavedAction(IWorkbenchWindow window) {
		super(WorkbenchMessages.CloseAllSavedAction_text, window);
		setToolTipText(WorkbenchMessages.CloseAllSavedAction_toolTip);
		setId("closeAllSaved"); //$NON-NLS-1$
		updateState();
		window.getWorkbench().getHelpSystem().setHelp(this, IWorkbenchHelpContextIds.CLOSE_ALL_SAVED_ACTION);
		setActionDefinitionId("org.eclipse.ui.file.closeAllSaved"); //$NON-NLS-1$
	}
