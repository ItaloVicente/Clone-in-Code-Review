	private IContributionManagerOverrides menuOverride;

	static class MenuOverrides implements IContributionManagerOverrides {

		private WorkbenchPage page;

		MenuOverrides(WorkbenchPage page) {
			this.page = page;
		}
