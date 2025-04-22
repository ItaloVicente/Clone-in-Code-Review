		if (commit.isStash()) {
			toolbar.add(createCommandContributionItem(StashApplyHandler.ID));
			toolbar.add(createCommandContributionItem(StashDropHandler.ID));
		} else {
			toolbar.add(createCommandContributionItem(CreateTagHandler.ID));
			toolbar.add(createCommandContributionItem(CreateBranchHandler.ID));
			toolbar.add(createCommandContributionItem(CheckoutHandler.ID));
			toolbar.add(createCommandContributionItem(CherryPickHandler.ID));
			toolbar.add(createCommandContributionItem(RevertHandler.ID));
			toolbar.add(createCommandContributionItem(ShowInHistoryHandler.ID));
		}
