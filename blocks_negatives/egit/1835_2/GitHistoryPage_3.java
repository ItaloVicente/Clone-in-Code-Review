	private CommandContributionItem getCommandContributionItem(
			String commandId, String menuLabel) {
		CommandContributionItemParameter parameter = new CommandContributionItemParameter(
				getSite(), commandId, commandId,
				CommandContributionItem.STYLE_PUSH);
		parameter.label = menuLabel;
		return new CommandContributionItem(parameter);
	}

	private CommandContributionItem getCommandContributionItem(
			String commandId, String menuLabel, Map<String, String> parameters) {
		CommandContributionItemParameter parameter = new CommandContributionItemParameter(
				getSite(), commandId, commandId,
				CommandContributionItem.STYLE_PUSH);
		parameter.label = menuLabel;
		parameter.parameters = parameters;
		return new CommandContributionItem(parameter);
	}

	private void attachContextMenu(final Control c) {
		if (c == graph.getControl()) {
			c.setMenu(popupMgr.createContextMenu(c));
			c.addMenuDetectListener(new MenuDetectListener() {
				public void menuDetected(MenuDetectEvent e) {
					popupMgr.removeAll();

					int selectionSize = ((IStructuredSelection) getSelectionProvider()
							.getSelection()).size();

					if (input.isSingleFile()) {
						if (selectionSize == 1)
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
						else if (selectionSize == 2)
							popupMgr
									.add(getCommandContributionItem(
											HistoryViewCommands.COMPARE_VERSIONS,
											UIText.GitHistoryPage_CompareWithEachOtherMenuLabel));
						if (selectionSize > 0) {
							popupMgr.add(getCommandContributionItem(
									HistoryViewCommands.OPEN,
									UIText.GitHistoryPage_OpenMenuLabel));
							popupMgr
									.add(getCommandContributionItem(
											HistoryViewCommands.OPEN_IN_TEXT_EDITOR,
											UIText.GitHistoryPage_OpenInTextEditorLabel));
						}
					}

					if (selectionSize == 1) {
						popupMgr.add(new Separator());
						popupMgr.add(getCommandContributionItem(
								HistoryViewCommands.CHECKOUT,
								UIText.GitHistoryPage_CheckoutMenuLabel));
						popupMgr.add(getCommandContributionItem(
								HistoryViewCommands.CREATE_BRANCH,
								UIText.GitHistoryPage_CreateBranchMenuLabel));
						popupMgr.add(getCommandContributionItem(
								HistoryViewCommands.CREATE_TAG,
								UIText.GitHistoryPage_CreateTagMenuLabel));
						popupMgr.add(getCommandContributionItem(
								HistoryViewCommands.CREATE_PATCH,
								UIText.GitHistoryPage_CreatePatchMenuLabel));
						popupMgr.add(getCommandContributionItem(
								HistoryViewCommands.CHERRYPICK,
								UIText.GitHistoryPage_cherryPickMenuItem));
						popupMgr.add(new Separator());

						MenuManager resetManager = new MenuManager(
								UIText.GitHistoryPage_ResetMenuLabel,
								UIIcons.RESET, "Reset"); //$NON-NLS-1$

						popupMgr.add(resetManager);

						Map<String, String> parameters = new HashMap<String, String>();
						parameters.put(HistoryViewCommands.RESET_MODE, "Soft"); //$NON-NLS-1$
						resetManager.add(getCommandContributionItem(
								HistoryViewCommands.RESET,
								UIText.GitHistoryPage_ResetSoftMenuLabel,
								parameters));
						parameters = new HashMap<String, String>();
						parameters.put(HistoryViewCommands.RESET_MODE, "Mixed"); //$NON-NLS-1$
						resetManager.add(getCommandContributionItem(
								HistoryViewCommands.RESET,
								UIText.GitHistoryPage_ResetMixedMenuLabel,
								parameters));
						parameters = new HashMap<String, String>();
						parameters.put(HistoryViewCommands.RESET_MODE, "Hard"); //$NON-NLS-1$
						resetManager.add(getCommandContributionItem(
								HistoryViewCommands.RESET,
								UIText.GitHistoryPage_ResetHardMenuLabel,
								parameters));
					}
					popupMgr.add(new Separator());

					MenuManager quickDiffManager = new MenuManager(
							UIText.GitHistoryPage_QuickdiffMenuLabel, null,

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
				}
			});
		} else if (c == commentViewer.getControl()) {
			final MenuManager mgr = new MenuManager();
			c.setMenu(mgr.createContextMenu(c));
			c.addMenuDetectListener(new MenuDetectListener() {
				public void menuDetected(MenuDetectEvent e) {
					if (mgr.isEmpty()) {
						mgr.add(selectAllAction);
						mgr.add(copyAction);
						mgr.add(new Separator());
						mgr.add(wrapCommentAction);
						mgr.add(fillCommentAction);
					}
				}
			});
		} else {
			final MenuManager mgr = new MenuManager();
			c.setMenu(mgr.createContextMenu(c));
			c.addMenuDetectListener(new MenuDetectListener() {
				public void menuDetected(MenuDetectEvent e) {
					if (mgr.isEmpty()) {
						mgr.add(selectAllAction);
						mgr.add(copyAction);
					}
				}
			});
		}
	}

