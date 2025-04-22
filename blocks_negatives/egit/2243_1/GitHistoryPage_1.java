	private void initActions() {
		IAction findAction = createFindToolbarAction();
		IAction refreshAction = new Action(
				UIText.GitHistoryPage_RefreshMenuLabel, UIIcons.ELCL16_REFRESH) {
			@Override
			public void run() {
				refresh();
			}
		};
		try {
			showAllFilter = ShowFilter.valueOf(Activator.getDefault()
					.getPreferenceStore().getString(PREF_SHOWALLFILTER));
		} catch (IllegalArgumentException e) {
			showAllFilter = ShowFilter.SHOWALLRESOURCE;
		}

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
				.setChecked(showAllFilter == showAllRepoVersionsAction.filter);
		showAllProjectVersionsAction
				.setChecked(showAllFilter == showAllProjectVersionsAction.filter);
		showAllFolderVersionsAction
				.setChecked(showAllFilter == showAllFolderVersionsAction.filter);
		showAllResourceVersionsAction
				.setChecked(showAllFilter == showAllResourceVersionsAction.filter);

		BooleanPrefAction compareModeAction = new BooleanPrefAction(
				UIPreferences.RESOURCEHISTORY_COMPARE_MODE,
				UIText.GitHistoryPage_CompareModeMenuLabel) {
			@Override
			void apply(boolean value) {
			}
		};
		actionsToDispose.add(compareModeAction);

		CompareUtils.ReuseCompareEditorAction reuseCompareEditorAction = new CompareUtils.ReuseCompareEditorAction();
		actionsToDispose.add(reuseCompareEditorAction);

		compareModeAction.setImageDescriptor(UIIcons.ELCL16_COMPARE_VIEW);
		compareModeAction.setToolTipText(UIText.GitHistoryPage_compareMode);

		BooleanPrefAction showAllBranchesAction = new BooleanPrefAction(
				UIPreferences.RESOURCEHISTORY_SHOW_ALL_BRANCHES,
				UIText.GitHistoryPage_ShowAllBranchesMenuLabel) {

			@Override
			void apply(boolean value) {
				refresh();
			}
		};
		actionsToDispose.add(showAllBranchesAction);

		showAllBranchesAction.setImageDescriptor(UIIcons.BRANCH);
		showAllBranchesAction
				.setToolTipText(UIText.GitHistoryPage_showAllBranches);

		final IAction showCommentAction = createShowComment();
		final IAction showFilesAction = createShowFiles();

		wrapCommentAction = createCommentWrap();
		fillCommentAction = createCommentFill();

		wrapCommentAction.setEnabled(showCommentAction.isChecked());
		fillCommentAction.setEnabled(showCommentAction.isChecked());

		IToolBarManager mgr = getSite().getActionBars().getToolBarManager();
		mgr.add(findAction);
		mgr.add(new Separator());
		mgr.add(showAllRepoVersionsAction);
		mgr.add(showAllProjectVersionsAction);
		mgr.add(showAllFolderVersionsAction);
		mgr.add(showAllResourceVersionsAction);
		mgr.add(new Separator());
		mgr.add(compareModeAction);
		mgr.add(showAllBranchesAction);

		IMenuManager viewMenuMgr = getSite().getActionBars().getMenuManager();
		viewMenuMgr.add(refreshAction);

		viewMenuMgr.add(new Separator());
		IMenuManager showSubMenuMgr = new MenuManager(
				UIText.GitHistoryPage_ShowSubMenuLabel);
		viewMenuMgr.add(showSubMenuMgr);
		showSubMenuMgr.add(showAllBranchesAction);
		showSubMenuMgr.add(findAction);
		showSubMenuMgr.add(showFilesAction);
		showSubMenuMgr.add(showCommentAction);

		IMenuManager filterSubMenuMgr = new MenuManager(
				UIText.GitHistoryPage_FilterSubMenuLabel);
		viewMenuMgr.add(filterSubMenuMgr);
		filterSubMenuMgr.add(showAllRepoVersionsAction);
		filterSubMenuMgr.add(showAllProjectVersionsAction);
		filterSubMenuMgr.add(showAllFolderVersionsAction);
		filterSubMenuMgr.add(showAllResourceVersionsAction);

		viewMenuMgr.add(new Separator());
		viewMenuMgr.add(compareModeAction);
		viewMenuMgr.add(reuseCompareEditorAction);

		viewMenuMgr.add(new Separator());
		viewMenuMgr.add(wrapCommentAction);
		viewMenuMgr.add(fillCommentAction);

		graph.getControl().addMenuDetectListener(new MenuDetectListener() {
			public void menuDetected(MenuDetectEvent e) {
				popupMgr.add(showFilesAction);
				popupMgr.add(showCommentAction);
			}
		});
	}

