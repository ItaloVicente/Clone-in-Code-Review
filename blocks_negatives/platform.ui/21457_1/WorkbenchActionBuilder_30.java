    
    private IContributionItem getSelectAllItem() {
		return getItem(
				ActionFactory.SELECT_ALL.getId(),
				ActionFactory.SELECT_ALL.getCommandId(),
				null, null, WorkbenchMessages.Workbench_selectAll,
