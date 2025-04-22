	private IContributionManagerOverrides toolbarOverride;

	static class ToolbarOverrides implements IContributionManagerOverrides {

		private WorkbenchPage page;

		ToolbarOverrides(WorkbenchPage page) {
			this.page = page;
		}
