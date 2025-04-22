	public CollapseAllAction(IResourceNavigator navigator, String label) {
		super(navigator, label);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this, INavigatorHelpContextIds.COLLAPSE_ALL_ACTION);
		setEnabled(true);
		setActionDefinitionId(CollapseAllHandler.COMMAND_ID);
	}
