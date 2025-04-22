    private IContributionItem getResetPerspectiveItem() {
		return getItem(
				ActionFactory.RESET_PERSPECTIVE.getId(),
				ActionFactory.RESET_PERSPECTIVE.getCommandId(),
				null,
				null,
				WorkbenchMessages.ResetPerspective_text,
				WorkbenchMessages.ResetPerspective_toolTip, 
				IWorkbenchHelpContextIds.RESET_PERSPECTIVE_ACTION);
    }
    
    private IContributionItem getSavePerspectiveItem() {
		return getItem(
				ActionFactory.SAVE_PERSPECTIVE.getId(),
				ActionFactory.SAVE_PERSPECTIVE.getCommandId(),
				null,
				null,
				WorkbenchMessages.SavePerspective_text,
				WorkbenchMessages.SavePerspective_toolTip, 
				IWorkbenchHelpContextIds.SAVE_PERSPECTIVE_ACTION);
    }
    
