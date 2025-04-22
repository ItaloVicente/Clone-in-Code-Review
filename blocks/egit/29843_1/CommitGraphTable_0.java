				popupMgr.add(getCommandContributionItem(
						HistoryViewCommands.COMPARE_WITH_PREVIOUS,
						UIText.GitHistoryPage_CompareWithPreviousMenuLabel));
				if (!input.isSingleFile())
					popupMgr.add(getCommandContributionItem(
							HistoryViewCommands.COMPARE_WITH_PREVIOUS_IN_TREE,
							UIText.GitHistoryPage_CompareWithPreviousInTreeMenuLabel));
				popupMgr.add(getCommandContributionItem(
						HistoryViewCommands.CHERRYPICK,
						UIText.GitHistoryPage_cherryPickMenuItem));
