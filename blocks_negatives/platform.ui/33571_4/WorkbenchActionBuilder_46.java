    
    private IContributionItem getRenameItem() {
		return getItem(ActionFactory.RENAME.getId(),
				ActionFactory.RENAME.getCommandId(), null, null,
				WorkbenchMessages.Workbench_rename,
				WorkbenchMessages.Workbench_renameToolTip, null);
