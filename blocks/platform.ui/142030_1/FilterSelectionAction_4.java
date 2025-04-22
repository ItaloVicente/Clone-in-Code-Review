	public FilterSelectionAction(IResourceNavigator navigator, String label) {
		super(navigator, label);
		setToolTipText(FILTER_TOOL_TIP);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this, INavigatorHelpContextIds.FILTER_SELECTION_ACTION);
		setEnabled(true);
	}
