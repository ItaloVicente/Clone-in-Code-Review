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
