		boolean filtersActive = inResources != null || inFiles != null;
		showAllRepoVersionsAction.setEnabled(filtersActive);
		showAllProjectVersionsAction.setEnabled(filtersActive);
		showAllFolderVersionsAction.setEnabled(inResources != null);
		showAllResourceVersionsAction.setEnabled(filtersActive);

		final AnyObjectId headId;
		try {
			headId = db.resolve(Constants.HEAD);
		} catch (IOException e) {
			String errorMessage = NLS.bind(
					UIText.GitHistoryPage_errorParsingHead, db.getDirectory()
							.getAbsolutePath());
			setErrorMessage(errorMessage);
			return false;
		}

		if (headId == null) {
			String errorMessage = NLS.bind(
					UIText.GitHistoryPage_errorParsingHead, Activator
							.getDefault().getRepositoryUtil()
							.getRepositoryName(db));
			setErrorMessage(errorMessage);
			return false;
		}

		if (pathChange(pathFilters, paths) || currentWalk == null
				|| !headId.equals(currentHeadId)) {
			currentHeadId = headId;
			if (currentWalk != null)
				currentWalk.release();
			currentWalk = new SWTWalk(db);
			currentWalk.sort(RevSort.COMMIT_TIME_DESC, true);
			currentWalk.sort(RevSort.BOUNDARY, true);
		} else {
			currentWalk.reset();
		}

		try {
			if (store.getBoolean(UIPreferences.RESOURCEHISTORY_SHOW_ALL_BRANCHES)) {
				markStartAllRefs(Constants.R_HEADS);
				markStartAllRefs(Constants.R_REMOTES);
			} else
				currentWalk.markStart(currentWalk.parseCommit(headId));
		} catch (IOException e) {
			Activator.logError(NLS.bind(
					UIText.GitHistoryPage_errorReadingHeadCommit, headId, db
							.getDirectory().getAbsolutePath()), e);
			return false;
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
		fileViewer.addSelectionChangedListener(commentViewer);
		commentViewer.setTreeWalk(fileWalker);
		commentViewer.setDb(db);
		findToolbar.clear();
		graph.setInput(highlightFlag, null, null);

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
		return true;
