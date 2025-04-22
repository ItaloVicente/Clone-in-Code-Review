	void initAndStartRevWalk(boolean forceNewWalk) throws IllegalStateException {
		cancelRefreshJob();
		Repository db = input.getRepository();
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

		List<String> paths = buildFilterPaths(input.getItems(), input
				.getFileList(), db);

		if (forceNewWalk || pathChange(pathFilters, paths)
				|| currentWalk == null || !headId.equals(currentHeadId)) {
			currentHeadId = headId;
			if (currentWalk != null)
				currentWalk.release();
			currentWalk = new SWTWalk(db);
			currentWalk.sort(RevSort.COMMIT_TIME_DESC, true);
			currentWalk.sort(RevSort.BOUNDARY, true);
			highlightFlag = currentWalk.newFlag("highlight"); //$NON-NLS-1$
		} else {
			currentWalk.reset();
		}

		try {
			if (store
					.getBoolean(UIPreferences.RESOURCEHISTORY_SHOW_ALL_BRANCHES)) {
				markStartAllRefs(Constants.R_HEADS);
				markStartAllRefs(Constants.R_REMOTES);
			} else
				currentWalk.markStart(currentWalk.parseCommit(headId));
		} catch (IOException e) {
			throw new IllegalStateException(NLS.bind(
					UIText.GitHistoryPage_errorReadingHeadCommit, headId, db
							.getDirectory().getAbsolutePath()), e);
		}

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
		fileViewer.setTreeWalk(db, fileWalker);
		fileViewer.refresh();
		fileViewer.addSelectionChangedListener(commentViewer);
		commentViewer.setTreeWalk(fileWalker);
		commentViewer.setDb(db);
		commentViewer.refresh();

		final SWTCommitList list;
		list = new SWTCommitList(graph.getControl().getDisplay());
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
		schedule(rj);
	}

