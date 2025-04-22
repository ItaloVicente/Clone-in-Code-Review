	private RevCommit fetchChange(String uri, RefSpec spec,
			IProgressMonitor monitor) throws CoreException, URISyntaxException,
			IOException {
		int timeout = Activator.getDefault().getPreferenceStore()
				.getInt(UIPreferences.REMOTE_CONNECTION_TIMEOUT);

		List<RefSpec> specs = new ArrayList<RefSpec>(1);
		specs.add(spec);

		String taskName = NLS
				.bind(UIText.FetchGerritChangePage_FetchingTaskName,
						spec.getSource());
		monitor.setTaskName(taskName);
		FetchResult fetchRes = new FetchOperationUI(repository,
				new URIish(uri), specs, timeout, false).execute(monitor);

		monitor.worked(1);
		return new RevWalk(repository).parseCommit(fetchRes.getAdvertisedRef(
				spec.getSource()).getObjectId());
	}

	private void createTag(final RefSpec spec, final String textForTag,
			RevCommit commit, IProgressMonitor monitor) throws CoreException {
		monitor.setTaskName(UIText.FetchGerritChangePage_CreatingTagTaskName);
		final TagBuilder tag = new TagBuilder();
		PersonIdent personIdent = new PersonIdent(repository);

		tag.setTag(textForTag);
		tag.setTagger(personIdent);
		tag.setMessage(NLS.bind(
				UIText.FetchGerritChangePage_GeneratedTagMessage,
				spec.getSource()));
		tag.setObjectId(commit);
		new TagOperation(repository, tag, false).execute(monitor);
		monitor.worked(1);
	}

	private void createBranch(final String textForBranch, RevCommit commit,
			IProgressMonitor monitor) throws CoreException, GitAPIException {
		monitor.setTaskName(UIText.FetchGerritChangePage_CreatingBranchTaskName);
		CreateLocalBranchOperation bop = new CreateLocalBranchOperation(
				repository, textForBranch, commit);
		bop.execute(monitor);
		CheckoutCommand co = new Git(repository).checkout();
		try {
			co.setName(textForBranch).call();
		} catch (CheckoutConflictException e) {
			final CheckoutResult result = co.getResult();

			if (result.getStatus() == Status.CONFLICTS) {
				final Shell shell = getWizard().getContainer().getShell();

				shell.getDisplay().asyncExec(new Runnable() {
					public void run() {
						new CheckoutConflictDialog(shell, repository, result
								.getConflictList()).open();
					}
				});
			}
		}
		monitor.worked(1);
	}

	private void checkout(RevCommit commit, IProgressMonitor monitor)
			throws CoreException {
		monitor.setTaskName(UIText.FetchGerritChangePage_CheckingOutTaskName);
		BranchOperationUI.checkout(repository, commit.name()).run(monitor);

		monitor.worked(1);
	}

	private void activateAdditionalRefs() {
		getContainer().getShell().getDisplay().asyncExec(new Runnable() {
			public void run() {
				Activator
						.getDefault()
						.getPreferenceStore()
						.setValue(
								UIPreferences.RESOURCEHISTORY_SHOW_ADDITIONAL_REFS,
								true);
			}
		});
	}

