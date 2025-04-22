    
    private IContributionItem getRefreshItem() {
		return getItem(ActionFactory.REFRESH.getId(),
				ActionFactory.REFRESH.getCommandId(), null, null,
				WorkbenchMessages.Workbench_refresh,
				WorkbenchMessages.Workbench_refreshToolTip, null);
