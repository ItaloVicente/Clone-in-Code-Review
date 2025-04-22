
	private ArrayList<IFile> notIndexed;
	private ArrayList<IFile> indexChanges;
	private ArrayList<IFile> notTracked;
	private ArrayList<IFile> files;

	private Commit previousCommit;

	private boolean amendAllowed;
	private boolean amending;

	@Override
	public void execute(IAction act) {
		if (!PlatformUI.getWorkbench().saveAllEditors(true)) {
			return;
		}

		resetState();
		try {
			buildIndexHeadDiffList();
		} catch (IOException e) {
			handle(
					new TeamException(UIText.CommitAction_errorComputingDiffs,
							e), UIText.CommitAction_errorDuringCommit,
					UIText.CommitAction_errorComputingDiffs);
			return;
		} catch (CoreException e) {
			handle(
					new TeamException(UIText.CommitAction_errorComputingDiffs,
							e), UIText.CommitAction_errorDuringCommit,
					UIText.CommitAction_errorComputingDiffs);
			return;
		}

		Repository[] repos = getRepositoriesFor(getProjectsForSelectedResources());
		Repository repository = null;
		amendAllowed = repos.length == 1;
		for (Repository repo : repos) {
			repository = repo;
			RepositoryState state = repo.getRepositoryState();
			if (state == RepositoryState.MERGING_RESOLVED || !state.canCommit()) {
				MessageDialog.openError(getTargetPart().getSite().getShell(),
					UIText.CommitAction_cannotCommit,
					NLS.bind(UIText.CommitAction_repositoryState, state.getDescription()));
				return;
			}
		}

		loadPreviousCommit();
		if (files.isEmpty()) {
			if (amendAllowed && previousCommit != null) {
				boolean result = MessageDialog
				.openQuestion(getTargetPart().getSite().getShell(),
						UIText.CommitAction_noFilesToCommit,
				UIText.CommitAction_amendCommit);
				if (!result)
					return;
				amending = true;
			} else {
				MessageDialog.openWarning(getTargetPart().getSite().getShell(), UIText.CommitAction_noFilesToCommit, UIText.CommitAction_amendNotPossible);
				return;
			}
		}

		String author = null;
		String committer = null;
		if (repository != null) {
			final RepositoryConfig config = repository.getConfig();
			author = config.getAuthorName();
			final String authorEmail = config.getAuthorEmail();

			committer = config.getCommitterName();
			final String committerEmail = config.getCommitterEmail();
		}

		CommitDialog commitDialog = new CommitDialog(getTargetPart().getSite().getShell());
		commitDialog.setAmending(amending);
		commitDialog.setAmendAllowed(amendAllowed);
		commitDialog.setFileList(files);
		commitDialog.setPreselectedFiles(getSelectedFiles());
		commitDialog.setAuthor(author);
		commitDialog.setCommitter(committer);

		if (previousCommit != null) {
			commitDialog.setPreviousCommitMessage(previousCommit.getMessage());
			PersonIdent previousAuthor = previousCommit.getAuthor();
			commitDialog.setPreviousAuthor(previousAuthor.getName()
		}

		if (commitDialog.open() != IDialogConstants.OK_ID)
			return;

		final CommitOperation commitOperation = new CommitOperation(
				commitDialog.getSelectedFiles(), notIndexed, notTracked,
				commitDialog.getAuthor(), commitDialog.getCommitter(),
				commitDialog.getCommitMessage());
		if (commitDialog.isAmending()) {
			commitOperation.setAmending(true);
			commitOperation.setPreviousCommit(previousCommit);
			commitOperation.setRepos(repos);
		}
		String jobname = UIText.CommitAction_CommittingChanges;
		Job job = new Job(jobname) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					commitOperation.execute(monitor);

					for (IProject proj : getProjectsForSelectedResources()) {
						RepositoryMapping.getMapping(proj).fireRepositoryChanged();
					}
				} catch (CoreException e) {
					return Activator.createErrorStatus(
							UIText.CommitAction_CommittingFailed, e);
				} finally {
					GitLightweightDecorator.refresh();
				}
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.schedule();
	}

	private void resetState() {
		files = new ArrayList<IFile>();
		notIndexed = new ArrayList<IFile>();
		indexChanges = new ArrayList<IFile>();
		notTracked = new ArrayList<IFile>();
		amending = false;
		previousCommit = null;
	}

