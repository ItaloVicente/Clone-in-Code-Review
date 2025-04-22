    
    private IContributionItem getResetPerspectiveItem() {
		return getItem(
				ActionFactory.RESET_PERSPECTIVE.getId(),
				ActionFactory.RESET_PERSPECTIVE.getCommandId(),
				null,
				null,
				WorkbenchMessages.ResetPerspective_text,
				WorkbenchMessages.ResetPerspective_toolTip, 
