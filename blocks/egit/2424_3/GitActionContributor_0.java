	@Override
	public void initialize(ISynchronizePageConfiguration configuration) {
		super.initialize(configuration);

		ExpandAllModelAction expandAllAction = new ExpandAllModelAction(
				GitActionContributor_ExpandAll, configuration);
		expandAllAction.setImageDescriptor(EXPAND_ALL);
		appendToGroup(P_TOOLBAR_MENU, NAVIGATE_GROUP, expandAllAction);
	}

