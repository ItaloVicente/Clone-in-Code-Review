		String taskName = NLS
				.bind(UIText.FetchGerritChangePage_FetchingTaskName,
						spec.getSource());
		monitor.subTask(taskName);
		FetchResult fetchRes = new FetchOperationUI(repository, new URIish(uri),
				specs, false).execute(monitor);

		monitor.worked(1);
		try (RevWalk rw = new RevWalk(repository)) {
			return rw.parseCommit(
					fetchRes.getAdvertisedRef(spec.getSource()).getObjectId());
		}
	}

	private void createTag(@NonNull RefSpec spec, @NonNull String textForTag,
			@NonNull RevCommit commit, IProgressMonitor monitor)
			throws CoreException {
		monitor.subTask(UIText.FetchGerritChangePage_CreatingTagTaskName);
		PersonIdent personIdent = new PersonIdent(repository);

		TagOperation operation = new TagOperation(repository)
				.setAnnotated(true)
				.setName(textForTag)
				.setTagger(personIdent)
				.setMessage(NLS.bind(
						UIText.FetchGerritChangePage_GeneratedTagMessage,
						spec.getSource()))
				.setTarget(commit)
				.setSign(Boolean.FALSE);
		operation.execute(monitor);
		monitor.worked(1);
	}

	private void createBranch(final String textForBranch, boolean doCheckout,
			RevCommit commit, IProgressMonitor monitor) throws CoreException {
		SubMonitor progress = SubMonitor.convert(monitor, doCheckout ? 10 : 2);
		progress.subTask(UIText.FetchGerritChangePage_CreatingBranchTaskName);
		CreateLocalBranchOperation bop = new CreateLocalBranchOperation(
				repository, textForBranch, commit);
		bop.execute(progress.newChild(2));
		if (doCheckout) {
			checkout(textForBranch, progress.newChild(8));
		}
	}

	private void checkout(String targetName, IProgressMonitor monitor)
			throws CoreException {
		monitor.subTask(UIText.FetchGerritChangePage_CheckingOutTaskName);
		BranchOperationUI.checkout(repository, targetName).run(monitor);
		monitor.worked(1);
	}

	private void cherryPick(@NonNull RevCommit commit,
			IProgressMonitor monitor) {
		monitor.subTask(UIText.FetchGerritChangePage_CherryPickTaskName);
		WorkbenchJob job = new WorkbenchJob(
				PlatformUI.getWorkbench().getDisplay(),
				UIText.FetchGerritChangePage_CherryPickTaskName) {

			@Override
			public IStatus runInUIThread(IProgressMonitor progress) {
				try {
					CherryPickUI ui = new CherryPickUI();
					ui.run(repository, commit, false);
				} catch (CoreException e) {
					return Activator.createErrorStatus(e.getLocalizedMessage(),
							e);
				} finally {
					progress.done();
				}
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.schedule();
		monitor.worked(1);
	}

	private void activateAdditionalRefs() {
		Activator.getDefault().getPreferenceStore().setValue(
				UIPreferences.RESOURCEHISTORY_SHOW_ADDITIONAL_REFS, true);
	}

	private ExplicitContentProposalAdapter addRefContentProposalToText(
			final Text textField) {
		return UIUtils.addContentProposalToText(textField, () -> {
			try {
				return getRefsForContentAssist();
			} catch (InvocationTargetException e) {
				Activator.handleError(e.getMessage(), e, true);
				return null;
			} catch (InterruptedException e) {
				return null;
			}
		}, (pattern, change) -> {
			if (pattern == null
					|| pattern.matcher(change.getRefName()).matches()) {
				return new ChangeContentProposal(change);
			}
			return null;
		}, input -> {
			Change change = determineChangeFromString(input);
			int changeNumber = -1;
			try {
				if (change == null) {
					Matcher matcher = DIGITS.matcher(input);
					if (matcher.find()) {
						return Pattern.compile(GERRIT_CHANGE_REF_PREFIX
					} else if (input.startsWith(GERRIT_CHANGE_REF_PREFIX)
							|| GERRIT_CHANGE_REF_PREFIX.startsWith(input)) {
					}
				} else {
					changeNumber = change.getChangeNumber().intValue();
				}
				if (changeNumber > 0) {
							+ changeNumber + WILDCARD);
				}
			} catch (PatternSyntaxException e) {
			}
			return UIUtils.createProposalPattern(input);
		}, null, UIText.FetchGerritChangePage_ContentAssistTooltip);
	}

	final static class Change implements Comparable<Change> {
