					popupMgr.removeAll();

					int selectionSize = ((IStructuredSelection) getSelectionProvider()
							.getSelection()).size();
					int type = ((IResource) getInput()).getType();

					if (type == IResource.FILE) {
						if (selectionSize == 1)
							popupMgr
									.add(getCommandContributionItem(
											HistoryViewCommands.COMPARE_WITH_TREE,
											UIText.GitHistoryPage_CompareWithWorkingTreeMenuMenuLabel));
						else if (selectionSize == 2)
							popupMgr
									.add(getCommandContributionItem(
											HistoryViewCommands.COMPARE_VERSIONS,
											UIText.GitHistoryPage_CompareWithEachOtherMenuLabel));
						if (selectionSize > 0)
							popupMgr.add(getCommandContributionItem(
									HistoryViewCommands.OPEN,
									UIText.GitHistoryPage_OpenMenuLabel));
					}

					if (selectionSize == 1) {
