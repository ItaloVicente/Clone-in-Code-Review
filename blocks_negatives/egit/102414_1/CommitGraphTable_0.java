	private final static class MenuListener implements MenuDetectListener {

		private final MenuManager popupMgr;

		private final ISelectionProvider selectionProvider;

		private final IPageSite site;

		private final IAction copyAction;

		private HistoryPageInput input;

		MenuListener(MenuManager menuManager,
				ISelectionProvider selectionProvider, IPageSite site,
				IAction copyAction) {
			this.popupMgr = menuManager;
			this.selectionProvider = selectionProvider;
			this.site = site;
			this.copyAction = copyAction;
		}

		public void setInput(HistoryPageInput input) {
			this.input = input;
		}

		@Override
		public void menuDetected(MenuDetectEvent e) {
			popupMgr.removeAll();

			final HistoryPageInput lastInput = this.input;
			if (lastInput == null)
				return;

			int selectionSize = ((IStructuredSelection) selectionProvider
					.getSelection()).size();

			if (lastInput.isSingleFile()) {
				if (selectionSize == 1)
					if (lastInput.getSingleFile() instanceof IResource)
						popupMgr
								.add(getCommandContributionItem(
										HistoryViewCommands.COMPARE_WITH_TREE,
										UIText.GitHistoryPage_CompareWithWorkingTreeMenuMenuLabel));
					else
						popupMgr
								.add(getCommandContributionItem(
										HistoryViewCommands.COMPARE_WITH_TREE,
										UIText.GitHistoryPage_CompareWithCurrentHeadMenu));
				if (selectionSize > 0) {
					popupMgr.add(getCommandContributionItem(
							HistoryViewCommands.OPEN,
							UIText.GitHistoryPage_OpenMenuLabel));
					popupMgr.add(getCommandContributionItem(
							HistoryViewCommands.OPEN_IN_TEXT_EDITOR,
							UIText.GitHistoryPage_OpenInTextEditorLabel));
				}
				if (selectionSize == 1)
					popupMgr.add(getCommandContributionItem(
							HistoryViewCommands.SHOW_BLAME,
							UIText.CommitFileDiffViewer_ShowAnnotationsMenuLabel));
			}

			if (selectionSize > 0) {
				popupMgr.add(getCommandContributionItem(
						HistoryViewCommands.OPEN_IN_COMMIT_VIEWER,
						UIText.CommitGraphTable_OpenCommitLabel));
			}

			if (selectionSize == 1) {
				popupMgr.add(new Separator());
				if (!lastInput.getRepository().isBare()) {
					if (hasMultipleRefNodes(lastInput)) {
						popupMgr.add(getCommandContributionItem(
								HistoryViewCommands.CHECKOUT,
								UIText.GitHistoryPage_CheckoutMenuLabel2));
					} else {
						popupMgr.add(getCommandContributionItem(
								HistoryViewCommands.CHECKOUT,
								UIText.GitHistoryPage_CheckoutMenuLabel));
					}
				}

				popupMgr.add(getCommandContributionItem(
						HistoryViewCommands.PUSH_COMMIT,
						UIText.GitHistoryPage_pushCommit));
				popupMgr.add(new Separator());
				popupMgr.add(getCommandContributionItem(
						HistoryViewCommands.CREATE_BRANCH,
						UIText.GitHistoryPage_CreateBranchMenuLabel));
				popupMgr.add(getCommandContributionItem(
						HistoryViewCommands.DELETE_BRANCH,
						UIText.CommitGraphTable_DeleteBranchAction));
				popupMgr.add(getCommandContributionItem(
						HistoryViewCommands.RENAME_BRANCH,
						UIText.CommitGraphTable_RenameBranchMenuLabel));
				popupMgr.add(getCommandContributionItem(
						HistoryViewCommands.CREATE_TAG,
						UIText.GitHistoryPage_CreateTagMenuLabel));
				popupMgr.add(getCommandContributionItem(
						HistoryViewCommands.DELETE_TAG,
						UIText.CommitGraphTable_DeleteTagAction));
				popupMgr.add(new Separator());
				popupMgr.add(getCommandContributionItem(
						HistoryViewCommands.CREATE_PATCH,
						UIText.GitHistoryPage_CreatePatchMenuLabel));
				popupMgr.add(getCommandContributionItem(
						HistoryViewCommands.CHERRYPICK,
						UIText.GitHistoryPage_cherryPickMenuItem));
				popupMgr.add(getCommandContributionItem(
						HistoryViewCommands.MERGE,
						UIText.GitHistoryPage_mergeMenuItem));
				popupMgr.add(getCommandContributionItem(
						HistoryViewCommands.REBASECURRENT,
						UIText.GitHistoryPage_rebaseMenuItem));
				popupMgr.add(getCommandContributionItem(
						HistoryViewCommands.REBASE_INTERACTIVE_CURRENT,
						UIText.GitHistoryPage_rebaseInteractiveMenuItem));
				popupMgr.add(new Separator());

				MenuManager resetManager = ResetMenu.createMenu(site);
				popupMgr.add(resetManager);
			} else if (selectionSize == 2) {
				popupMgr.add(getCommandContributionItem(
						HistoryViewCommands.COMPARE_VERSIONS,
						UIText.GitHistoryPage_CompareWithEachOtherMenuLabel));
				if (!lastInput.isSingleFile())
					popupMgr
							.add(getCommandContributionItem(
									HistoryViewCommands.COMPARE_VERSIONS_IN_TREE,
									UIText.CommitGraphTable_CompareWithEachOtherInTreeMenuLabel));
			}

			popupMgr.add(new Separator());

			popupMgr.add(getCommandContributionItem(HistoryViewCommands.REVERT,
					UIText.GitHistoryPage_revertMenuItem));

			popupMgr.add(new Separator());

			MenuManager quickDiffManager = new MenuManager(
					UIText.GitHistoryPage_QuickdiffMenuLabel, null, "Quickdiff"); //$NON-NLS-1$

			popupMgr.add(quickDiffManager);

			quickDiffManager.add(getCommandContributionItem(
					HistoryViewCommands.SET_QUICKDIFF_BASELINE,
					UIText.GitHistoryPage_SetAsBaselineMenuLabel));

			Map<String, String> parameters = new HashMap<>();
			parameters.put(HistoryViewCommands.BASELINE_TARGET, "HEAD"); //$NON-NLS-1$
			quickDiffManager.add(getCommandContributionItem(
					HistoryViewCommands.RESET_QUICKDIFF_BASELINE,
					UIText.GitHistoryPage_ResetBaselineToHeadMenuLabel,
					parameters));

			parameters = new HashMap<>();
			parameters.put(HistoryViewCommands.BASELINE_TARGET, "HEAD^1"); //$NON-NLS-1$
			quickDiffManager.add(getCommandContributionItem(
					HistoryViewCommands.RESET_QUICKDIFF_BASELINE,
					UIText.GitHistoryPage_ResetBaselineToParentOfHeadMenuLabel,
					parameters));

			popupMgr.add(new Separator());

			MenuManager modifyManager = new MenuManager(
					UIText.GitHistoryPage_ModifyMenuLabel, null, "Modify"); //$NON-NLS-1$

			popupMgr.add(modifyManager);

			if (selectionSize == 1) {
				modifyManager.add(getCommandContributionItem(
						HistoryViewCommands.REWORD,
						UIText.GitHistoryPage_rewordMenuItem));
				modifyManager.add(getCommandContributionItem(
						HistoryViewCommands.EDIT,
						UIText.GitHistoryPage_editMenuItem));
			}

			if (selectionSize >= 2)
				modifyManager.add(getCommandContributionItem(
						HistoryViewCommands.SQUASH,
						UIText.GitHistoryPage_squashMenuItem));

			popupMgr.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
			popupMgr.add(copyAction);
			popupMgr.add(new Separator());
		}

		private boolean hasMultipleRefNodes(HistoryPageInput lastInput) {
			try {
				Map<String, Ref> branches = lastInput.getRepository()
						.getRefDatabase().getRefs(Constants.R_HEADS);
				int count = 0;
				IStructuredSelection selection = (IStructuredSelection) selectionProvider
						.getSelection();
				if (selection.isEmpty()) {
					return false;
				}
				ObjectId selectedId = ((RevCommit) selection.getFirstElement())
						.getId();
				for (Ref branch : branches.values()) {
					ObjectId objectId = branch.getLeaf().getObjectId();
					if (objectId != null && objectId.equals(selectedId)) {
						count++;
					}
				}
				return (count > 1);

			} catch (IOException e) {
			}
			return false;
		}

		private CommandContributionItem getCommandContributionItem(
				String commandId, String menuLabel) {
			CommandContributionItemParameter parameter = new CommandContributionItemParameter(
					site, commandId, commandId,
					CommandContributionItem.STYLE_PUSH);
			parameter.label = menuLabel;
			return new CommandContributionItem(parameter);
		}

		private CommandContributionItem getCommandContributionItem(
				String commandId, String menuLabel,
				Map<String, String> parameters) {
			CommandContributionItemParameter parameter = new CommandContributionItemParameter(
					site, commandId, commandId,
					CommandContributionItem.STYLE_PUSH);
			parameter.label = menuLabel;
			parameter.parameters = parameters;
			return new CommandContributionItem(parameter);
		}
	}
