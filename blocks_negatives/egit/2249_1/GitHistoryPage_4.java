	private AnyObjectId resolveHead(Repository db) {
		AnyObjectId headId;
		try {
			headId = db.resolve(Constants.HEAD);
		} catch (IOException e) {
			throw new IllegalStateException(NLS.bind(
					UIText.GitHistoryPage_errorParsingHead, Activator
							.getDefault().getRepositoryUtil()
							.getRepositoryName(db)));
		}
		if (headId == null)
			throw new IllegalStateException(NLS.bind(
					UIText.GitHistoryPage_errorParsingHead, Activator
							.getDefault().getRepositoryUtil()
							.getRepositoryName(db)));
		return headId;
	}

	private void createNewWalk(Repository db, AnyObjectId headId) {
		currentHeadId = headId;
		if (currentWalk != null)
			currentWalk.release();
		currentWalk = new SWTWalk(db);
		currentWalk.sort(RevSort.COMMIT_TIME_DESC, true);
		currentWalk.sort(RevSort.BOUNDARY, true);
	}

	private void setWalkStartPoints(Repository db, AnyObjectId headId) {
		try {
			if (store
					.getBoolean(UIPreferences.RESOURCEHISTORY_SHOW_ALL_BRANCHES)) {
				markStartAllRefs(Constants.R_HEADS);
				markStartAllRefs(Constants.R_REMOTES);
			} else
				currentWalk.markStart(currentWalk.parseCommit(headId));
		} catch (IOException e) {
			throw new IllegalStateException(NLS.bind(
					UIText.GitHistoryPage_errorReadingHeadCommit, headId,
					db.getDirectory().getAbsolutePath()), e);
		}
	}

	private void setupCommentViewer(Repository db, final TreeWalk fileWalker) {
		commentViewer.setTreeWalk(fileWalker);
		commentViewer.setDb(db);
		commentViewer.refresh();
	}

	private TreeWalk setupFileViewer(Repository db, List<String> paths) {
		final TreeWalk fileWalker = createFileWalker(db, paths);
		fileViewer.setTreeWalk(db, fileWalker);
		fileViewer.refresh();
		fileViewer.addSelectionChangedListener(commentViewer);
		return fileWalker;
	}

	private TreeWalk createFileWalker(Repository db, List<String> paths) {
		final TreeWalk fileWalker = new TreeWalk(db);
		fileWalker.setRecursive(true);
		if (paths.size() > 0) {
			pathFilters = paths;
			currentWalk.setTreeFilter(AndTreeFilter.create(PathFilterGroup
					.createFromStrings(paths), TreeFilter.ANY_DIFF));
			fileWalker.setFilter(currentWalk.getTreeFilter().clone());

		} else {
			pathFilters = null;
			currentWalk.setTreeFilter(TreeFilter.ALL);
			fileWalker.setFilter(TreeFilter.ANY_DIFF);
		}
		return fileWalker;
	}

	private void scheduleNewGenerateHistoryJob() {
		final SWTCommitList list = new SWTCommitList(graph.getControl().getDisplay());
		list.source(currentWalk);
		final GenerateHistoryJob rj = new GenerateHistoryJob(this, list);
		rj.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(final IJobChangeEvent event) {
				final Control graphctl = graph.getControl();
				if (job != rj || graphctl.isDisposed())
					return;
				graphctl.getDisplay().asyncExec(new Runnable() {
					public void run() {
						if (job == rj)
							job = null;
					}
				});
			}
		});
		job = rj;
		if (trace)
			GitTraceLocation.getTrace().trace(
					GitTraceLocation.HISTORYVIEW.getLocation(),
		schedule(rj);
	}

	/**
	 * @param compareMode
	 *            switch compare mode button of the view on / off
	 */
	public void setCompareMode(boolean compareMode) {
		store.setValue(UIPreferences.RESOURCEHISTORY_COMPARE_MODE, compareMode);
	}

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

	/**
	 * @return the selection provider
	 */
	public ISelectionProvider getSelectionProvider() {
		return graph.getTableView();
	}

	private Runnable refschangedRunnable;

	public void onRefsChanged(final RefsChangedEvent e) {
		if (input == null || e.getRepository() != input.getRepository())
			return;

		if (getControl().isDisposed())
			return;

		synchronized (this) {
			if (refschangedRunnable == null) {
				refschangedRunnable = new Runnable() {
					public void run() {
						if (!getControl().isDisposed()) {
							if (GitTraceLocation.HISTORYVIEW.isActive())
								GitTraceLocation
										.getTrace()
										.trace(
												GitTraceLocation.HISTORYVIEW
														.getLocation(),
							refschangedRunnable = null;
							initAndStartRevWalk(true);
						}
					}
				};
				getControl().getDisplay().asyncExec(refschangedRunnable);
			}
		}
	}

	private void layoutSashForm(final SashForm sf, final String key) {
		sf.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				final int[] w = sf.getWeights();
				store.putValue(key, UIPreferences.intArrayToString(w));
				if (store.needsSaving())
					try {
						store.save();
					} catch (IOException e1) {
						Activator.handleError(e1.getMessage(), e1, false);
					}

			}
		});
		sf.setWeights(UIPreferences.stringToIntArray(store.getString(key), 2));
	}

	private Composite createMainPanel(final Composite parent) {
		topControl = new Composite(parent, SWT.NONE);
		StackLayout layout = new StackLayout();
		topControl.setLayout(layout);

		final Composite c = new Composite(topControl, SWT.NULL);
		layout.topControl = c;
		errorText = new StyledText(topControl, SWT.NONE);
		errorText.setFont(UIUtils
				.getFont(UIPreferences.THEME_CommitMessageFont));
		errorText.setText(UIText.CommitFileDiffViewer_SelectOneCommitMessage);

		final GridLayout parentLayout = new GridLayout();
		parentLayout.marginHeight = 0;
		parentLayout.marginWidth = 0;
		parentLayout.verticalSpacing = 0;
		c.setLayout(parentLayout);
		return c;
