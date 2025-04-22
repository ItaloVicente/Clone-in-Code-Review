		for (int i = 0; i < adjunctContributions.size(); i++) {
			ActionSetContribution contribution = (ActionSetContribution) adjunctContributions
					.get(i);
			ActionSetActionBars bars = actionSet.getBars();

			for (int j = 0; j < contribution.adjunctActions.size(); j++) {
				ActionDescriptor adjunctAction = (ActionDescriptor) contribution.adjunctActions
						.get(j);
				contribution.contributeAdjunctCoolbarAction(adjunctAction, bars);
			}

			for (int j = 0; j < contribution.actions.size(); j++) {
				ActionDescriptor action = (ActionDescriptor) contribution.actions.get(j);
				if (action.getMenuPath() != null) {
					contribution.contributeMenuAction(action, bars.getMenuManager(), true);
				}
			}
		}
