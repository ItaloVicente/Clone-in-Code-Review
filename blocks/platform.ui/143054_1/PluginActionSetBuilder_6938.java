			toolbarMgr.update(true);
		}
	}

	protected void removeActionExtensions(PluginActionSet set, IWorkbenchWindow window) {
		this.actionSet = set;
		this.window = window;
		currentContribution = null;
		targetID = null;
		targetContributionTag = IWorkbenchRegistryConstants.TAG_ACTION_SET;
		String id = set.getDesc().getId();

		if (cache != null) {
			for (int i = 0; i < cache.size(); i++) {
				ActionSetContribution contribution = (ActionSetContribution) cache.get(i);
				contribution.revokeContribution((WorkbenchWindow) window, actionSet.getBars(), id);
				if (contribution.isAdjunctContributor()) {
					for (int j = 0; j < contribution.adjunctActions.size(); j++) {
						ActionDescriptor adjunctAction = (ActionDescriptor) contribution.adjunctActions.get(j);
						contribution.revokeAdjunctCoolbarAction(adjunctAction, actionSet.getBars());
					}
				}
			}
		}
	}
