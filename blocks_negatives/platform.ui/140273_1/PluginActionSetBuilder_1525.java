                }
                insertIndex = nX;
            } else {
                break;
            }
        }
        return items[insertIndex];
    }

    /**
     */
    /* package */static void processActionSets(ArrayList pluginActionSets,
            WorkbenchWindow window) {
        PluginActionSetBuilder[] builders = new PluginActionSetBuilder[pluginActionSets
                .size()];
        for (int i = 0; i < pluginActionSets.size(); i++) {
            PluginActionSet set = (PluginActionSet) pluginActionSets.get(i);
            PluginActionSetBuilder builder = new PluginActionSetBuilder();
            builder.readActionExtensions(set, window);
            builders[i] = builder;
        }
        for (PluginActionSetBuilder builder : builders) {
            builder.processAdjunctContributions();
        }
    }

    /**
     */
    protected void processAdjunctContributions() {
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
    }

    /**
     * Read the actions within a config element.
     */
    protected void readActionExtensions(PluginActionSet set,
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

            registerBinding(set);

        } else {
            WorkbenchPlugin
        }
    }

    private void registerBinding(final PluginActionSet set) {
    	final IExtensionTracker tracker = window.getExtensionTracker();

    	final Binding binding = new Binding();
        binding.builder = this;
        binding.set = set;
        binding.window = window;
