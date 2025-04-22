
	private IContributionItem getDeleteItem() {
		return getItem(ActionFactory.DELETE.getId(),
				ActionFactory.DELETE.getCommandId(),
				ISharedImages.IMG_TOOL_DELETE,
				ISharedImages.IMG_TOOL_DELETE_DISABLED,
				WorkbenchMessages.Workbench_delete,
				WorkbenchMessages.Workbench_deleteToolTip,
				IWorkbenchHelpContextIds.DELETE_RETARGET_ACTION);
	}

	private IContributionItem getRevertItem() {
		return getItem(ActionFactory.REVERT.getId(),
				ActionFactory.REVERT.getCommandId(), null, null,
				WorkbenchMessages.Workbench_revert,
