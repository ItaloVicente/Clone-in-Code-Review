		final Shell shell = getPart(event).getSite().getShell();

		int parentIndex = -1;
		if (commit.getParentCount() > 1) {
			List<RevCommit> parents = new ArrayList<>();
			String branch = null;
			try {
				for (RevCommit parent : commit.getParents()) {
					parents.add(repo.parseCommit(parent));
				}
				branch = repo.getBranch();
			} catch (Exception e) {
				Activator.handleError(e.getLocalizedMessage(), e, true);
				return null;
			}
			CommitSelectDialog selectCommit = new CommitSelectDialog(shell,
					parents, getLaunchMessage(repo));
			selectCommit.create();
			selectCommit.setTitle(UIText.CommitSelectDialog_ChooseParentTitle);
			selectCommit.setMessage(MessageFormat.format(
					UIText.CherryPickHandler_CherryPickMergeMessage,
					commit.abbreviate(7).name(), branch));
			if (selectCommit.open() != Window.OK) {
				return null;
			}
			parentIndex = parents.indexOf(selectCommit.getSelectedCommit());
		} else if (!confirmCherryPick(shell, repo, commit)) {
			return null;
		}

		doCherryPick(repo, commit, parentIndex, true);
		return null;
	}

	private void doCherryPick(@NonNull Repository repo, RevCommit commit,
			int parentIndex, boolean withCleanup) {
		CherryPickOperation op = new CherryPickOperation(repo, commit);
		op.setMainlineIndex(parentIndex);

		Job job = new RepositoryJob(MessageFormat.format(
				UIText.CherryPickHandler_JobName, Integer.valueOf(1)), null) {

			private CherryPickResult result;

			@Override
			protected IStatus performJob(IProgressMonitor monitor) {
				try {
					op.execute(monitor);
					result = op.getResult();
					if (!withCleanup
							&& result.getStatus() == CherryPickStatus.FAILED) {
						return getErrorList(result.getFailingPaths());
					}
				} catch (CoreException e) {
					return Activator.createErrorStatus(
							UIText.CherryPickOperation_InternalError, e);
				}
				return Status.OK_STATUS;
			}

			@Override
			protected IAction getAction() {
				RevCommit newHead = result.getNewHead();
				if (newHead == null) {
					switch (result.getStatus()) {
					case CONFLICTING:
						return new MessageAction(
								UIText.CherryPickHandler_CherryPickConflictsTitle,
								UIText.CherryPickHandler_CherryPickConflictsMessage);
					case FAILED:
						if (!withCleanup) {
							return new RepositoryJobResultAction(repo,
									UIText.CherryPickHandler_CherryPickFailedMessage) {

								@Override
								protected void showResult(
										Repository repository) {
									Activator.showErrorStatus(
											UIText.CherryPickHandler_CherryPickFailedMessage,
											getErrorList(
													result.getFailingPaths()));
								}
							};
						}
						return new CleanupAction(repo,
								UIText.CherryPickHandler_UncommittedFilesTitle,
								result, () -> doCherryPick(repo, commit,
										parentIndex, false));
					case OK:
						return null;
					}
				} else if (result.getCherryPickedRefs().isEmpty()) {
					return new MessageAction(
							UIText.CherryPickHandler_NoCherryPickPerformedTitle,
							UIText.CherryPickHandler_NoCherryPickPerformedMessage);
				}
				return null;
			}

			@Override
			public boolean belongsTo(Object family) {
				if (JobFamilies.CHERRY_PICK.equals(family)) {
					return true;
				}
				return super.belongsTo(family);
			}

		};
		job.setUser(true);
		job.setRule(op.getSchedulingRule());
		job.schedule();
	}

	private String getLaunchMessage(Repository repository) {
		ILaunchConfiguration launch = LaunchFinder
				.getRunningLaunchConfiguration(
						Collections.singleton(repository), null);
		if (launch != null) {
			return MessageFormat.format(
					UIText.LaunchFinder_RunningLaunchMessage, launch.getName());
		}
		return null;
	}

	private boolean confirmCherryPick(final Shell shell,
			final Repository repository, final RevCommit commit)
			throws ExecutionException {
		final AtomicBoolean confirmed = new AtomicBoolean(false);
		String message;
