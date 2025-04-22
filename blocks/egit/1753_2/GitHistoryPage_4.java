							if (getInput() instanceof IFile)
								popupMgr
										.add(getCommandContributionItem(
												HistoryViewCommands.COMPARE_WITH_TREE,
												UIText.GitHistoryPage_CompareWithWorkingTreeMenuMenuLabel));
							else
								popupMgr
										.add(getCommandContributionItem(
												HistoryViewCommands.COMPARE_WITH_TREE,
												UIText.GitHistoryPage_CompareWithCurrentHeadMenuLabel));
