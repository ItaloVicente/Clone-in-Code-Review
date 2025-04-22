	@Override
	public void createControl(final Composite parent) {
		trace = GitTraceLocation.HISTORYVIEW.isActive();
		if (trace)
			GitTraceLocation.getTrace().traceEntry(
					GitTraceLocation.HISTORYVIEW.getLocation());
	
		historyControl = createMainPanel(parent);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(historyControl);
		graphDetailSplit = new SashForm(historyControl, SWT.VERTICAL);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(
				graphDetailSplit);
		graph = new CommitGraphTable(graphDetailSplit, getSite(), popupMgr);
		revInfoSplit = new SashForm(graphDetailSplit, SWT.HORIZONTAL);
		commentViewer = new CommitMessageViewer(revInfoSplit, getSite());
		fileViewer = new CommitFileDiffViewer(revInfoSplit, getSite());
		findToolbar = new FindToolbar(historyControl);
	
		layoutSashForm(graphDetailSplit,
				UIPreferences.RESOURCEHISTORY_GRAPH_SPLIT);
		layoutSashForm(revInfoSplit, UIPreferences.RESOURCEHISTORY_REV_SPLIT);
	
		attachCommitSelectionChanged();
		initActions();
	
		getSite().registerContextMenu(POPUP_ID, popupMgr, graph.getTableView());
		layout();
	
		myRefsChangedHandle = Repository.getGlobalListenerList()
				.addRefsChangedListener(this);
	
		if (trace)
			GitTraceLocation.getTrace().traceExit(
					GitTraceLocation.HISTORYVIEW.getLocation());
	}
