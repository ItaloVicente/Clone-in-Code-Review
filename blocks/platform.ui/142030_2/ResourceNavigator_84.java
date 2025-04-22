	protected void updateActionBars(IStructuredSelection selection) {
		ResourceNavigatorActionGroup group = getActionGroup();
		if (group != null) {
			group.setContext(new ActionContext(selection));
			group.updateActionBars();
		}
	}

	protected void updateStatusLine(IStructuredSelection selection) {
		String msg = getStatusLineMessage(selection);
		getViewSite().getActionBars().getStatusLineManager().setMessage(msg);
	}

	public void updateTitle() {
		Object input = getViewer().getInput();
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkingSet workingSet = workingSetFilter.getWorkingSet();

		if (input == null || input.equals(workspace) || input.equals(workspace.getRoot())) {
			setContentDescription(""); //$NON-NLS-1$
			if (workingSet != null) {
				setTitleToolTip(
						NLS.bind(ResourceNavigatorMessages.ResourceNavigator_workingSetToolTip, workingSet.getLabel()));
			} else {
				setTitleToolTip(""); //$NON-NLS-1$
			}
		} else {
			ILabelProvider labelProvider = (ILabelProvider) getTreeViewer().getLabelProvider();
			String inputToolTip = getFrameToolTipText(input);
			String text = labelProvider.getText(input);
			if (text != null) {
