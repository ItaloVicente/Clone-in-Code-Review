                    revokeActionSetFromMenu((IMenuManager) item,
                            actionsetId);
                } else if (item instanceof ActionSetContributionItem) {
                    id = ((ActionSetContributionItem) item)
                            .getActionSetId();
                    if (actionsetId.equals(id)) {
