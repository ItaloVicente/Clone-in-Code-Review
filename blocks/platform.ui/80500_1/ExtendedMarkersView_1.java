	private void initToolBar() {
		IActionBars bars = getViewSite().getActionBars();
		IToolBarManager tm = bars.getToolBarManager();
		createFilterAction();
		tm.add(new Separator("filterGroup")); //$NON-NLS-1$
		tm.add(filterAction);
	}

