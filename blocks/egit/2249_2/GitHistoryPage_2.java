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
