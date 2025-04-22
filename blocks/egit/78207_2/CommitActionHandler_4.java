	private IResource[] getResourcesInScope(ExecutionEvent event)
			throws ExecutionException {
		try {
			IResource[] selectedResources = getSelectedResources(event);
			if (selectedResources.length > 0) {
				IWorkbenchPart part = getPart(event);
				return GitScopeUtil.getRelatedChanges(part, selectedResources);
			} else {
				return new IResource[0];
			}
		} catch (InterruptedException e) {
			return null;
		}
	}

	private IndexDiffData getIndexDiffData(final @NonNull Repository repository,
			final @NonNull Collection<IProject> projects) {
		IndexDiffCacheEntry diffCacheEntry = org.eclipse.egit.core.Activator
				.getDefault().getIndexDiffCache()
				.getIndexDiffCacheEntry(repository);
		IndexDiffData diff = null;
		if (diffCacheEntry != null) {
			diff = diffCacheEntry.getIndexDiff();
		}
		if (diff != null) {
			return diff;
		}
		final IndexDiffData[] result = { null };
		try {
			PlatformUI.getWorkbench().getProgressService()
					.busyCursorWhile(new IRunnableWithProgress() {

						@Override
						public void run(IProgressMonitor monitor)
								throws InvocationTargetException,
								InterruptedException {
							try {
								result[0] = new IndexDiffData(
										CommitUI.getIndexDiff(repository,
												projects.toArray(
														new IProject[projects
																.size()]),
												monitor));
							} catch (IOException e) {
								throw new InvocationTargetException(e);
							}
						}
					});
		} catch (InvocationTargetException e) {
			Activator.handleError(UIText.CommitAction_errorComputingDiffs,
					e.getCause(), true);
			return null;
		} catch (InterruptedException e) {
			return null;
		}
		return result[0];
	}

	private void autoStage(final @NonNull Repository repository,
			boolean includeUntracked, IResource[] resourcesInScope) {
		if (resourcesInScope == null || resourcesInScope.length == 0) {
			return;
		}
		final Set<IProject> projects = new HashSet<>();
		for (IResource rsc : resourcesInScope) {
			projects.add(rsc.getProject());
		}
		IndexDiffData diff = getIndexDiffData(repository, projects);
		if (diff == null) {
			return;
		}
		Set<String> mayBeCommitted = new HashSet<>();
		mayBeCommitted.addAll(diff.getAdded());
		mayBeCommitted.addAll(diff.getChanged());
		mayBeCommitted.addAll(diff.getRemoved());
		mayBeCommitted.addAll(diff.getModified());
		mayBeCommitted.addAll(diff.getMissing());
		if (!includeUntracked) {
			mayBeCommitted.removeAll(diff.getUntracked());
		} else {
			mayBeCommitted.addAll(diff.getUntracked());
		}
		mayBeCommitted.removeAll(diff.getAssumeUnchanged());
		final Set<String> toBeStaged = CommitUI.getSelectedFiles(repository,
				mayBeCommitted, resourcesInScope);
		Job job = new Job(UIText.AddToIndexAction_addingFiles) {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				SubMonitor progress = SubMonitor.convert(monitor,
						toBeStaged.size() + 1);
				try (Git git = new Git(repository)) {
					AddCommand add = git.add();
					for (String toStage : toBeStaged) {
						add.addFilepattern(toStage);
						progress.worked(1);
					}
					if (progress.isCanceled()) {
						return Status.CANCEL_STATUS;
					}
					add.call();
				} catch (GitAPIException e) {
					return Activator.createErrorStatus(
							CoreText.AddToIndexOperation_failed, e);
				} finally {
					monitor.done();
				}
				return Status.OK_STATUS;
			}

			@Override
			public boolean belongsTo(Object family) {
				return JobFamilies.ADD_TO_INDEX.equals(family)
						|| super.belongsTo(family);
			}

		};
		job.setUser(true);
		job.setRule(RuleUtil.getRule(repository));
		job.schedule();
	}

