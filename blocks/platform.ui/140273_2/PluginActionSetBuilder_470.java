	}

	public PluginActionSetBuilder() {
	}

	public void buildMenuAndToolBarStructure(PluginActionSet set, IWorkbenchWindow window) {
		this.actionSet = set;
		this.window = window;
		cache = null;
		currentContribution = null;
		targetID = null;
		targetContributionTag = IWorkbenchRegistryConstants.TAG_ACTION_SET;

		readElements(new IConfigurationElement[] { set.getConfigElement() });

		if (cache != null) {
			for (int i = 0; i < cache.size(); i++) {
				ActionSetContribution contribution = (ActionSetContribution) cache.get(i);
				contribution.contribute(actionSet.getBars(), true, true);
				if (contribution.isAdjunctContributor()) {
					adjunctContributions.add(contribution);
				}
			}
		}
		for (int i = 0; i < adjunctContributions.size(); i++) {
			ActionSetContribution contribution = (ActionSetContribution) adjunctContributions.get(i);
			ActionSetActionBars bars = actionSet.getBars();
			for (int j = 0; j < contribution.adjunctActions.size(); j++) {
				ActionDescriptor adjunctAction = (ActionDescriptor) contribution.adjunctActions.get(j);
				contribution.contributeAdjunctCoolbarAction(adjunctAction, bars);
			}
		}

		registerBinding(set);
	}

	@Override
	protected ActionDescriptor createActionDescriptor(IConfigurationElement element) {
		boolean pullDownStyle = false;
		String style = element.getAttribute(IWorkbenchRegistryConstants.ATT_STYLE);
		if (style != null) {
			pullDownStyle = style.equals(ActionDescriptor.STYLE_PULLDOWN);
