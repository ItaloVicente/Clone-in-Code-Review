    
    private IContributionItem getPrintItem() {
		return getItem(
				ActionFactory.PRINT.getId(),
				ActionFactory.PRINT.getCommandId(),
				ISharedImages.IMG_ETOOL_PRINT_EDIT,
				ISharedImages.IMG_ETOOL_PRINT_EDIT_DISABLED,
				WorkbenchMessages.Workbench_print,
				WorkbenchMessages.Workbench_printToolTip, null);
