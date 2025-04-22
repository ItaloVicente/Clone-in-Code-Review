	public ToggleEditorsVisibilityAction(IWorkbenchWindow window) {
		super(window);
		setText(WorkbenchMessages.ToggleEditor_hideEditors);
		setActionDefinitionId("org.eclipse.ui.window.hideShowEditors"); //$NON-NLS-1$
		setToolTipText(WorkbenchMessages.ToggleEditor_toolTip);
		window.getWorkbench().getHelpSystem().setHelp(this, IWorkbenchHelpContextIds.TOGGLE_EDITORS_VISIBILITY_ACTION);
		window.addPerspectiveListener(this);
	}
