		if (realInput != null) {
			cancelJob();
			realInput.setInterestingPaths(interestingPaths);
			startJob(realInput);
		}
	}

	private TreeFilter toFilter(Collection<String> paths) {
		if (paths != null && !paths.isEmpty()) {
			return PathFilterGroup.createFromStrings(paths);
		} else {
			return TreeFilter.ALL;
		}
	}

	private void startJob(FileDiffInput input) {
		FileDiffLoader job = new FileDiffLoader(input,
				toFilter(input.getInterestingPaths()));
		job.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				if (!event.getResult().isOK()) {
					return;
				}
				UIJob updater = new UpdateJob(MessageFormat.format(
						UIText.CommitFileDiffViewer_updatingFileDiffs,
						input.getCommit().getName()), job);
				updater.schedule();
			}
		});
		job.setUser(false);
		job.setSystem(true);
		loader = job;
		loader.schedule();
	}

	private void cancelJob() {
		if (loader != null) {
			loader.cancel();
			loader = null;
		}
	}

	private static class FileDiffLoader extends Job {

		private FileDiff[] diffs;

		private final FileDiffInput input;

		private final TreeFilter filter;

		public FileDiffLoader(FileDiffInput input, TreeFilter filter) {
			super(MessageFormat.format(
					UIText.CommitFileDiffViewer_computingFileDiffs,
					input.getCommit().getName()));
			this.input = input;
			this.filter = filter;
			setRule(new TreeWalkSchedulingRule(input.getTreeWalk()));
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			try {
				if (monitor.isCanceled()) {
					return Status.CANCEL_STATUS;
				}
				diffs = FileDiff.compute(input.getRepository(),
						input.getTreeWalk(), input.getCommit(), monitor,
						filter);
			} catch (IOException err) {
				Activator.handleError(MessageFormat.format(
						UIText.CommitFileDiffViewer_errorGettingDifference,
						input.getCommit().getId()), err, false);
			}
			if (monitor.isCanceled()) {
				return Status.CANCEL_STATUS;
			}
			return Status.OK_STATUS;
		}

		public FileDiff[] getDiffs() {
			return diffs;
		}

		public FileDiffInput getInput() {
			return input;
		}

		@Override
		public boolean belongsTo(Object family) {
			return family == JobFamilies.HISTORY_FILE_DIFF
					|| super.belongsTo(family);
		}
	}

	private class UpdateJob extends UIJob {

		private final FileDiffLoader loadJob;

		public UpdateJob(String name, FileDiffLoader loadJob) {
			super(name);
			this.loadJob = loadJob;
		}

		@Override
		public IStatus runInUIThread(IProgressMonitor monitor) {
			Control control = getControl();
			if (control == null || control.isDisposed() || loader != loadJob) {
				return Status.CANCEL_STATUS;
			}
			FileDiff[] diffs = loadJob.getDiffs();
			try {
				control.setRedraw(false);
				setInput(diffs);
				FileDiff interesting = getFirstInterestingElement(diffs);
				if (interesting != null) {
					if (loadJob.getInput().isSelectMarked()) {
						setSelection(new StructuredSelection(interesting),
								true);
					} else {
						reveal(interesting);
					}
				}
			} finally {
				control.setRedraw(true);
			}
			return Status.OK_STATUS;
		}

		private FileDiff getFirstInterestingElement(FileDiff[] diffs) {
			if (diffs != null) {
				for (FileDiff d : diffs) {
					if (d.isMarked(INTERESTING_MARK_TREE_FILTER_INDEX)) {
						return d;
					}
				}
			}
			return null;
		}

