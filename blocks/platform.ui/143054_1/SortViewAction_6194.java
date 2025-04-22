	public SortViewAction(IResourceNavigator navigator, boolean sortByType) {
		super(navigator,
				sortByType ? ResourceNavigatorMessages.SortView_byType : ResourceNavigatorMessages.SortView_byName);
		if (sortByType) {
			setToolTipText(ResourceNavigatorMessages.SortView_toolTipByType);
		} else {
			setToolTipText(ResourceNavigatorMessages.SortView_toolTipByName);
		}
		setEnabled(true);
		sortCriteria = sortByType ? ResourceComparator.TYPE : ResourceComparator.NAME;
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this, INavigatorHelpContextIds.SORT_VIEW_ACTION);
	}
