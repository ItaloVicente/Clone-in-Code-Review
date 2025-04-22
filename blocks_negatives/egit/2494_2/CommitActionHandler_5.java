		Repository repository = null;
		Repository mergeRepository = null;
		amendAllowed = repos.length == 1;
		boolean isMergedResolved = false;
		for (Repository repo : repos) {
			repository = repo;
			RepositoryState state = repo.getRepositoryState();
			if (!state.canCommit()) {
				MessageDialog.openError(getShell(event),
						UIText.CommitAction_cannotCommit, NLS.bind(
								UIText.CommitAction_repositoryState, state
										.getDescription()));
				return null;
			}
			else if (state.equals(RepositoryState.MERGING_RESOLVED)) {
				isMergedResolved = true;
				mergeRepository = repo;
			}
		}

		loadPreviousCommit(event);
		if (files.isEmpty()) {
			if (amendAllowed && previousCommit != null) {
				boolean result = MessageDialog.openQuestion(getShell(event),
						UIText.CommitAction_noFilesToCommit,
						UIText.CommitAction_amendCommit);
				if (!result)
					return null;
				amending = true;
			} else {
				MessageDialog.openWarning(getShell(event),
						UIText.CommitAction_noFilesToCommit,
						UIText.CommitAction_amendNotPossible);
				return null;
			}
		}

		String author = null;
		String committer = null;
		if (repository != null) {
			final UserConfig config = repository.getConfig().get(UserConfig.KEY);
			author = config.getAuthorName();
			final String authorEmail = config.getAuthorEmail();

			committer = config.getCommitterName();
			final String committerEmail = config.getCommitterEmail();
		}

		CommitDialog commitDialog = new CommitDialog(getShell(event));
		commitDialog.setAmending(amending);
		commitDialog.setAmendAllowed(amendAllowed);
		commitDialog.setFiles(files, indexDiffs);
		commitDialog.setPreselectedFiles(getSelectedFiles(event));
		commitDialog.setAuthor(author);
		commitDialog.setCommitter(committer);
		commitDialog.setAllowToChangeSelection(!isMergedResolved);

		if (previousCommit != null) {
			commitDialog.setPreviousCommitMessage(previousCommit.getFullMessage());
			PersonIdent previousAuthor = previousCommit.getAuthorIdent();
			commitDialog.setPreviousAuthor(previousAuthor.getName()
		}
		if (isMergedResolved) {
			commitDialog.setCommitMessage(getMergeResolveMessage(mergeRepository, event));
		}

		if (commitDialog.open() != IDialogConstants.OK_ID)
			return null;

		final CommitOperation commitOperation = new CommitOperation(
				commitDialog.getSelectedFiles(), notIndexed, notTracked,
				commitDialog.getAuthor(), commitDialog.getCommitter(),
				commitDialog.getCommitMessage());
		if (commitDialog.isAmending()) {
			commitOperation.setAmending(true);
			commitOperation.setPreviousCommit(previousCommit);
			commitOperation.setRepos(repos);
		}
		commitOperation.setComputeChangeId(commitDialog.getCreateChangeId());
		commitOperation.setCommitAll(isMergedResolved);
		if (isMergedResolved)
			commitOperation.setRepos(repos);
		String jobname = UIText.CommitAction_CommittingChanges;
		Job job = new Job(jobname) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					commitOperation.execute(monitor);

					for (IProject proj : getProjectsForSelectedResources(event)) {
						RepositoryMapping.getMapping(proj)
								.fireRepositoryChanged();
					}
				} catch (CoreException e) {
					return Activator.createErrorStatus(
							UIText.CommitAction_CommittingFailed, e);
				} catch (ExecutionException e) {
					return Activator.createErrorStatus(
							UIText.CommitAction_CommittingFailed, e);
				} finally {
					GitLightweightDecorator.refresh();
				}
				return Status.OK_STATUS;
			}

			@Override
			public boolean belongsTo(Object family) {
				if (family.equals(JobFamilies.COMMIT))
					return true;
				return super.belongsTo(family);
			}

		};
		job.setUser(true);
		job.schedule();
		return null;
	}

	private void resetState() {
		files = new LinkedHashSet<IFile>();
		notIndexed = new LinkedHashSet<IFile>();
		indexChanges = new LinkedHashSet<IFile>();
		notTracked = new LinkedHashSet<IFile>();
		amending = false;
		previousCommit = null;
		indexDiffs = new HashMap<Repository, IndexDiff>();
	}

	/**
	 * Retrieves a collection of files that may be committed based on the user's
	 * selection when they performed the commit action. That is, even if the
	 * user only selected one folder when the action was performed, if the
	 * folder contains any files that could be committed, they will be returned.
	 *
	 * @param event
	 *
	 * @return a collection of files that is eligible to be committed based on
	 *         the user's selection
	 * @throws ExecutionException
	 */
	private Set<IFile> getSelectedFiles(ExecutionEvent event)
			throws ExecutionException {
		Set<IFile> preselectionCandidates = new LinkedHashSet<IFile>();
