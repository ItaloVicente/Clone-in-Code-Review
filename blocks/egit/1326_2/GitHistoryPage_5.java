					popupMgr.add(new Separator());

					MenuManager quickDiffManager = new MenuManager(
							UIText.GitHistoryPage_QuickdiffMenuLabel, null,
							"Quickdiff"); //$NON-NLS-1$

					popupMgr.add(quickDiffManager);

					quickDiffManager.add(getCommandContributionItem(
							HistoryViewCommands.SET_QUICKDIFF_BASELINE,
							UIText.GitHistoryPage_SetAsBaselineMenuLabel));

					Map<String, String> parameters = new HashMap<String, String>();
					parameters.put(HistoryViewCommands.BASELINE_TARGET, "HEAD"); //$NON-NLS-1$
					quickDiffManager.add(getCommandContributionItem(
							HistoryViewCommands.RESET_QUICKDIFF_BASELINE,
							UIText.GitHistoryPage_ResetBaselineToHeadMenuLabel,
							parameters));

					parameters = new HashMap<String, String>();
					parameters.put(HistoryViewCommands.BASELINE_TARGET,
							"HEAD^1"); //$NON-NLS-1$
					quickDiffManager
							.add(getCommandContributionItem(
									HistoryViewCommands.RESET_QUICKDIFF_BASELINE,
									UIText.GitHistoryPage_ResetBaselineToParentOfHeadMenuLabel,
									parameters));

					popupMgr.add(new Separator(
							IWorkbenchActionConstants.MB_ADDITIONS));
					popupMgr.add(copyAction);
					popupMgr.add(new Separator());
					popupMgr.add(showCommentAction);
					popupMgr.add(showFilesAction);
