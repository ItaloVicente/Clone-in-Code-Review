    
    private IContributionItem getOpenProjectItem() {
		return getItem(IDEActionFactory.OPEN_PROJECT.getId(),
				IDEActionFactory.OPEN_PROJECT.getCommandId(), null, null,
				IDEWorkbenchMessages.OpenResourceAction_text,
				IDEWorkbenchMessages.OpenResourceAction_toolTip, null);
