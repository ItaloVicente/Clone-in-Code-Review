	public PropertyDialogAction(IShellProvider shell, ISelectionProvider provider) {
		super(provider, WorkbenchMessages.PropertyDialog_text);
		Assert.isNotNull(shell);
		this.shellProvider = shell;
		setToolTipText(WorkbenchMessages.PropertyDialog_toolTip);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IWorkbenchHelpContextIds.PROPERTY_DIALOG_ACTION);
	}
