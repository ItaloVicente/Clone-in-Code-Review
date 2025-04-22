    
    private IContributionItem getFindItem() {
		return getItem(
				ActionFactory.FIND.getId(),
				ActionFactory.FIND.getCommandId(),
				null, null, WorkbenchMessages.Workbench_findReplace,
				WorkbenchMessages.Workbench_findReplaceToolTip, null);
