		RepositoryCommit commit = getCommit();
		Repository repository = commit.getRepository();
		Map<String, Ref> refsMap = getAllBranchRefs(repository);
		if (refsMap.isEmpty()) {
			return Collections.emptyList();
		}
		if (refsMap.size() < 42) {
			return findBranchesReachableFromCommit(commit, refsMap);
		} else {
			Job branchRefreshJob = new Job(
					MessageFormat.format(UIText.CommitEditorPage_JobName,
							commit.getRevCommit().name())) {

				@Override
				protected IStatus run(IProgressMonitor monitor) {
					final List<Ref> branches = findBranchesReachableFromCommit(
							commit, refsMap);

					final ScrolledForm form = getManagedForm().getForm();
					if (UIUtils.isUsable(form) && !monitor.isCanceled())
						form.getDisplay().syncExec(new Runnable() {

							@Override
							public void run() {
								if (!UIUtils.isUsable(form)) {
									return;
								}
								fillBranches(branches);
								form.reflow(true);
								form.layout(true, true);
							}
						});

					return Status.OK_STATUS;
				}

				@Override
				public boolean belongsTo(Object family) {
					return CommitEditorPage.this == family;
				}
			};
			branchRefreshJob.setRule(this);
			branchRefreshJob.schedule();
			return Collections.emptyList();
		}
	}

	private List<Ref> findBranchesReachableFromCommit(RepositoryCommit commit,
			Map<String, Ref> refsMap) {
		try (RevWalk revWalk = new RevWalk(commit.getRepository())) {
			return RevWalkUtils.findBranchesReachableFrom(commit.getRevCommit(),
					revWalk, refsMap.values());
		} catch (IOException e) {
			Activator.handleError(e.getMessage(), e, false);
			return Collections.emptyList();
		}
	}

	private Map<String, Ref> getAllBranchRefs(Repository repository) {
		Map<String, Ref> refsMap = new HashMap<>();
		try {
