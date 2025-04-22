		provider = prov;
		this.part = part;
		readContributions(id, IWorkbenchRegistryConstants.TAG_VIEWER_CONTRIBUTION,
				IWorkbenchRegistryConstants.PL_POPUP_MENU);
		return (cache != null);
	}

	private static class ViewerContribution extends BasicContribution implements ISelectionChangedListener {
		private ISelectionProvider selProvider;

		private ActionExpression visibilityTest;

		public ViewerContribution(ISelectionProvider selProvider) {
			super();
			this.selProvider = selProvider;
