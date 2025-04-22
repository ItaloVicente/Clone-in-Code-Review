    }

    /**
     * Constructs a new builder.
     */
    public PluginActionSetBuilder() {
    }

    /**
     * Read the actions within a config element. Called by customize perspective
     *
     * @param set the action set
     * @param window the window to contribute to
     */
    public void buildMenuAndToolBarStructure(PluginActionSet set,
            IWorkbenchWindow window) {
        this.actionSet = set;
        this.window = window;
        cache = null;
        currentContribution = null;
        targetID = null;
        targetContributionTag = IWorkbenchRegistryConstants.TAG_ACTION_SET;

        readElements(new IConfigurationElement[] { set.getConfigElement() });

        if (cache != null) {
            for (int i = 0; i < cache.size(); i++) {
                ActionSetContribution contribution = (ActionSetContribution) cache
                        .get(i);
                contribution.contribute(actionSet.getBars(), true, true);
                if (contribution.isAdjunctContributor()) {
                    adjunctContributions.add(contribution);
                }
            }
        }
        for (int i = 0; i < adjunctContributions.size(); i++) {
            ActionSetContribution contribution = (ActionSetContribution) adjunctContributions
                    .get(i);
            ActionSetActionBars bars = actionSet.getBars();
            for (int j = 0; j < contribution.adjunctActions.size(); j++) {
                ActionDescriptor adjunctAction = (ActionDescriptor) contribution.adjunctActions
                        .get(j);
                contribution
                        .contributeAdjunctCoolbarAction(adjunctAction, bars);
            }
        }

        registerBinding(set);
    }

    @Override
	protected ActionDescriptor createActionDescriptor(
            IConfigurationElement element) {
        boolean pullDownStyle = false;
        String style = element.getAttribute(IWorkbenchRegistryConstants.ATT_STYLE);
        if (style != null) {
            pullDownStyle = style.equals(ActionDescriptor.STYLE_PULLDOWN);
        } else {
            String pulldown = element.getAttribute(ActionDescriptor.STYLE_PULLDOWN);
        }

        ActionDescriptor desc = null;
        if (pullDownStyle) {
			desc = new ActionDescriptor(element,
                    ActionDescriptor.T_WORKBENCH_PULLDOWN, window);
