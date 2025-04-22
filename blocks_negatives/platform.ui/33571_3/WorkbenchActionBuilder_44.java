    
    private IContributionItem getPropertiesItem() {
		return getItem(ActionFactory.PROPERTIES.getId(),
				ActionFactory.PROPERTIES.getCommandId(), null, null,
				WorkbenchMessages.Workbench_properties,
				WorkbenchMessages.Workbench_propertiesToolTip, null);
