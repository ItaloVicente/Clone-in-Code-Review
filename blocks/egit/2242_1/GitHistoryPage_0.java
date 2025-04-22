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
		highlightFlag = currentWalk.newFlag("highlight"); //$NON-NLS-1$
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
					"Scheduling GenerateHistoryJob"); //$NON-NLS-1$
		schedule(rj);
	}

