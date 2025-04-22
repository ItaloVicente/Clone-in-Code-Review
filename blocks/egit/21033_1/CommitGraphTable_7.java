			} else if (selectionSize >= 2) {
				if (selectionSize == 2) {
					popupMgr.add(getCommandContributionItem(
							HistoryViewCommands.COMPARE_VERSIONS,
							UIText.GitHistoryPage_CompareWithEachOtherMenuLabel));
					if (!input.isSingleFile())
						popupMgr.add(getCommandContributionItem(
								HistoryViewCommands.COMPARE_VERSIONS_IN_TREE,
								UIText.CommitGraphTable_CompareWithEachOtherInTreeMenuLabel));
				}

				popupMgr.add(new Separator());

