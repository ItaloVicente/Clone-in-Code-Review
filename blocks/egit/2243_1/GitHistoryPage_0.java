
	private static class GitHistoryPageActions {

		private abstract class BooleanPrefAction extends Action implements
				IPropertyChangeListener, IWorkbenchAction {
			private final String prefName;

			BooleanPrefAction(final String pn, final String text) {
				setText(text);
				prefName = pn;
				historyPage.store.addPropertyChangeListener(this);
				setChecked(historyPage.store.getBoolean(prefName));
			}

			public void run() {
				historyPage.store.setValue(prefName, isChecked());
				if (historyPage.store.needsSaving()) {
					try {
						historyPage.store.save();
					} catch (IOException e) {
						Activator.handleError(e.getMessage(), e, false);
					}
				}
			}

			abstract void apply(boolean value);

			public void propertyChange(final PropertyChangeEvent event) {
				if (prefName.equals(event.getProperty())) {
					setChecked(historyPage.store.getBoolean(prefName));
					apply(isChecked());
				}
			}

			public void dispose() {
				historyPage.store.removePropertyChangeListener(this);
			}
		}

		private class ShowFilterAction extends Action {
			private final ShowFilter filter;

			ShowFilterAction(ShowFilter filter, ImageDescriptor icon,
					String menuLabel, String toolTipText) {
				super(null, IAction.AS_CHECK_BOX);
				this.filter = filter;
				setImageDescriptor(icon);
				setText(menuLabel);
				setToolTipText(toolTipText);
			}

			@Override
			public void run() {
				String oldName = historyPage.getName();
				String oldDescription = historyPage.getDescription();
				if (!isChecked()) {
					if (historyPage.showAllFilter == filter) {
						historyPage.showAllFilter = ShowFilter.SHOWALLRESOURCE;
						showAllResourceVersionsAction.setChecked(true);
						historyPage.initAndStartRevWalk(false);
					}
				}
				if (isChecked() && historyPage.showAllFilter != filter) {
					historyPage.showAllFilter = filter;
					if (this != showAllRepoVersionsAction)
						showAllRepoVersionsAction.setChecked(false);
					if (this != showAllProjectVersionsAction)
						showAllProjectVersionsAction.setChecked(false);
					if (this != showAllFolderVersionsAction)
						showAllFolderVersionsAction.setChecked(false);
					if (this != showAllResourceVersionsAction)
						showAllResourceVersionsAction.setChecked(false);
					historyPage.initAndStartRevWalk(false);
				}
				historyPage.firePropertyChange(historyPage, P_NAME, oldName,
						historyPage.getName());
				historyPage.firePropertyChange(historyPage, P_DESCRIPTION,
						oldDescription, historyPage.getDescription());
				Activator
						.getDefault()
						.getPreferenceStore()
						.setValue(PREF_SHOWALLFILTER,
								historyPage.showAllFilter.toString());
			}

			@Override
			public String toString() {
				return "ShowFilter[" + filter.toString() + "]"; //$NON-NLS-1$ //$NON-NLS-2$
			}
		}

		List<IWorkbenchAction> actionsToDispose;

		BooleanPrefAction wrapCommentAction;

		BooleanPrefAction fillCommentAction;

		IAction findAction;

		IAction refreshAction;

		BooleanPrefAction showCommentAction;

		BooleanPrefAction showFilesAction;

		IWorkbenchAction compareModeAction;

		IWorkbenchAction showAllBranchesAction;

		IWorkbenchAction reuseCompareEditorAction;

		ShowFilterAction showAllRepoVersionsAction;

		ShowFilterAction showAllProjectVersionsAction;

		ShowFilterAction showAllFolderVersionsAction;

		ShowFilterAction showAllResourceVersionsAction;

		private GitHistoryPage historyPage;

		GitHistoryPageActions(GitHistoryPage historyPage) {
			actionsToDispose = new ArrayList<IWorkbenchAction>();
			this.historyPage = historyPage;
			createActions();
		}

		private void createActions() {
			createFindToolbarAction();
			createRefreshAction();
			createFilterActions();
			createCompareModeAction();
			createReuseCompareEditorAction();
			createShowAllBranchesAction();
			createShowCommentAction();
			createShowFilesAction();
			createWrapCommentAction();
			createFillCommentAction();

			wrapCommentAction.setEnabled(showCommentAction.isChecked());
			fillCommentAction.setEnabled(showCommentAction.isChecked());
		}

		private void createFindToolbarAction() {
			findAction = new Action(UIText.GitHistoryPage_FindMenuLabel,
					UIIcons.ELCL16_FIND) {
				public void run() {
					historyPage.store.setValue(
							UIPreferences.RESOURCEHISTORY_SHOW_FINDTOOLBAR,
							isChecked());
					if (historyPage.store.needsSaving()) {
						try {
							historyPage.store.save();
						} catch (IOException e) {
							Activator.handleError(e.getMessage(), e, false);
						}
					}
					historyPage.layout();
				}
			};
			findAction
					.setChecked(historyPage.store
							.getBoolean(UIPreferences.RESOURCEHISTORY_SHOW_FINDTOOLBAR));
			findAction.setToolTipText(UIText.GitHistoryPage_FindTooltip);
		}

		private void createRefreshAction() {
			refreshAction = new Action(UIText.GitHistoryPage_RefreshMenuLabel,
					UIIcons.ELCL16_REFRESH) {
				@Override
				public void run() {
					historyPage.refresh();
				}
			};
		}

		private void createFilterActions() {
			showAllRepoVersionsAction = new ShowFilterAction(
					ShowFilter.SHOWALLREPO, UIIcons.REPOSITORY,
					UIText.GitHistoryPage_AllInRepoMenuLabel,
					UIText.GitHistoryPage_AllInRepoTooltip);

			showAllProjectVersionsAction = new ShowFilterAction(
					ShowFilter.SHOWALLPROJECT, UIIcons.FILTERPROJECT,
					UIText.GitHistoryPage_AllInProjectMenuLabel,
					UIText.GitHistoryPage_AllInProjectTooltip);

			showAllFolderVersionsAction = new ShowFilterAction(
					ShowFilter.SHOWALLFOLDER, UIIcons.FILTERFOLDER,
					UIText.GitHistoryPage_AllInParentMenuLabel,
					UIText.GitHistoryPage_AllInParentTooltip);

			showAllResourceVersionsAction = new ShowFilterAction(
					ShowFilter.SHOWALLRESOURCE, UIIcons.FILTERRESOURCE,
					UIText.GitHistoryPage_AllOfResourceMenuLabel,
					UIText.GitHistoryPage_AllOfResourceTooltip);

			showAllRepoVersionsAction
					.setChecked(historyPage.showAllFilter == showAllRepoVersionsAction.filter);
			showAllProjectVersionsAction
					.setChecked(historyPage.showAllFilter == showAllProjectVersionsAction.filter);
			showAllFolderVersionsAction
					.setChecked(historyPage.showAllFilter == showAllFolderVersionsAction.filter);
			showAllResourceVersionsAction
					.setChecked(historyPage.showAllFilter == showAllResourceVersionsAction.filter);
		}

		private void createCompareModeAction() {
			compareModeAction = new BooleanPrefAction(
					UIPreferences.RESOURCEHISTORY_COMPARE_MODE,
					UIText.GitHistoryPage_CompareModeMenuLabel) {
				@Override
				void apply(boolean value) {
				}
			};
			compareModeAction.setImageDescriptor(UIIcons.ELCL16_COMPARE_VIEW);
			compareModeAction.setToolTipText(UIText.GitHistoryPage_compareMode);
			actionsToDispose.add(compareModeAction);
		}

		private void createReuseCompareEditorAction() {
			reuseCompareEditorAction = new CompareUtils.ReuseCompareEditorAction();
			actionsToDispose.add(reuseCompareEditorAction);
		}

		private void createShowAllBranchesAction() {
			showAllBranchesAction = new BooleanPrefAction(
					UIPreferences.RESOURCEHISTORY_SHOW_ALL_BRANCHES,
					UIText.GitHistoryPage_ShowAllBranchesMenuLabel) {

				@Override
				void apply(boolean value) {
					historyPage.refresh();
				}
			};
			showAllBranchesAction.setImageDescriptor(UIIcons.BRANCH);
			showAllBranchesAction
					.setToolTipText(UIText.GitHistoryPage_showAllBranches);
			actionsToDispose.add(showAllBranchesAction);
		}

		private void createShowCommentAction() {
			showCommentAction = new BooleanPrefAction(
					UIPreferences.RESOURCEHISTORY_SHOW_REV_COMMENT,
					UIText.ResourceHistory_toggleRevComment) {
				void apply(final boolean value) {
					historyPage.layout();
					wrapCommentAction.setEnabled(isChecked());
					fillCommentAction.setEnabled(isChecked());
				}
			};
			actionsToDispose.add(showCommentAction);
		}

		private void createShowFilesAction() {
			showFilesAction = new BooleanPrefAction(
					UIPreferences.RESOURCEHISTORY_SHOW_REV_DETAIL,
					UIText.ResourceHistory_toggleRevDetail) {
				void apply(final boolean value) {
					historyPage.layout();
				}
			};
			actionsToDispose.add(showFilesAction);
		}

		private void createWrapCommentAction() {
			wrapCommentAction = new BooleanPrefAction(
					UIPreferences.RESOURCEHISTORY_SHOW_COMMENT_WRAP,
					UIText.ResourceHistory_toggleCommentWrap) {
				void apply(boolean wrap) {
				}
			};
			wrapCommentAction.apply(wrapCommentAction.isChecked());
			actionsToDispose.add(wrapCommentAction);
		}

		private void createFillCommentAction() {
			fillCommentAction = new BooleanPrefAction(
					UIPreferences.RESOURCEHISTORY_SHOW_COMMENT_FILL,
					UIText.ResourceHistory_toggleCommentFill) {
				void apply(boolean fill) {
				}
			};
			fillCommentAction.apply(fillCommentAction.isChecked());
			actionsToDispose.add(fillCommentAction);
		}
	}

