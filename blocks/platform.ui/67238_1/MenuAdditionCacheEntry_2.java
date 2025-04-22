	private MToolBarElement createToolDynamicAddition(IConfigurationElement element) {
		String id = MenuHelper.getId(element);
		MToolControl control = RenderedElementUtil.createRenderedToolBarElement();
		control.setElementId(id);
		control.setContributionURI(CompatibilityWorkbenchWindowControlContribution.CONTROL_CONTRIBUTION_URI);
		ControlContributionRegistry.add(id, element);
		control.setVisibleWhen(MenuHelper.getVisibleWhen(element));
		createIdentifierTracker(control);
		return control;
	}

