							if (input.getSingleFile() instanceof IResource)
								popupMgr
										.add(getCommandContributionItem(
												HistoryViewCommands.COMPARE_WITH_TREE,
												UIText.GitHistoryPage_CompareWithWorkingTreeMenuMenuLabel));
							else
								popupMgr
										.add(getCommandContributionItem(
												HistoryViewCommands.COMPARE_WITH_TREE,
												UIText.GitHistoryPage_CompareWithCurrentHeadMenu));
