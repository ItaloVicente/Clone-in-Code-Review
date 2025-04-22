    
    private IContributionItem getMoveItem() {
		return getItem(ActionFactory.MOVE.getId(),
				ActionFactory.MOVE.getCommandId(),
				null, null, WorkbenchMessages.Workbench_move,
				WorkbenchMessages.Workbench_moveToolTip, null);
