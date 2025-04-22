
	private IContributionItem getRevertItem() {
		return getItem(ActionFactory.REVERT.getId(), ActionFactory.REVERT.getCommandId(), null, null,
				WorkbenchMessages.Workbench_revert, WorkbenchMessages.Workbench_revertToolTip, null);
	}

	private IContributionItem getRefreshItem() {
		return getItem(ActionFactory.REFRESH.getId(), ActionFactory.REFRESH.getCommandId(), null, null,
				WorkbenchMessages.Workbench_refresh, WorkbenchMessages.Workbench_refreshToolTip, null);
