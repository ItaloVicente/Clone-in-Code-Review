			toolbar.add(createActionContributionItem(CreateTagHandler.ID,
					UIText.CommitEditor_toolbarCreateTag, UIIcons.TAG));
			toolbar.add(createActionContributionItem(CreateBranchHandler.ID,
					UIText.CommitEditor_toolbarCreateBranch, UIIcons.BRANCH));
			toolbar.add(createActionContributionItem(CheckoutHandler.ID,
					UIText.CommitEditor_toolbarCheckOut, UIIcons.CHECKOUT));
			toolbar.add(createActionContributionItem(CherryPickHandler.ID,
					UIText.CommitEditor_toolbarCherryPick,
					UIIcons.CHERRY_PICK));
			toolbar.add(createActionContributionItem(RevertHandler.ID,
					UIText.CommitEditor_toolbarRevert, UIIcons.REVERT));
			toolbar.add(createActionContributionItem(ShowInHistoryHandler.ID,
					UIText.CommitEditor_toolbarShowInHistory, UIIcons.HISTORY));
