
	private void createDiffs() {
		if (diffComposite != null)
			diffComposite.dispose();

		RevCommit commit = revision.getCommit();
		if (commit.getParentCount() == 0)
			return;

		createDiffComposite();

		for (int i = 0; i < commit.getParentCount(); i++) {
			RevCommit parent = commit.getParent(i);
			createDiff(parent);
		}
	}

	private void createDiffComposite() {
		diffComposite = new Composite(displayArea, SWT.NONE);
		diffComposite.setLayoutData(GridDataFactory.fillDefaults()
				.grab(true, true).create());
		diffComposite.setLayout(GridLayoutFactory.fillDefaults().create());
	}

	private void createDiff(RevCommit parent) {
		Diff diff = revision.getDiffToParent(parent);
		if (diff != null) {
			try {
				createDiffLinkAndText(parent, diff);
			} catch (IOException e) {
				String msg = "Error creating diff in blame information control for commit " //$NON-NLS-1$
						+ parent.toObjectId();
				Activator.logError(msg, e);
			}
		}
	}

	private void createDiffLinkAndText(final RevCommit parent, final Diff diff)
			throws IOException {
		String parentId = parent.toObjectId().abbreviate(7).name();
		String parentMessage = parent.getShortMessage();

		EditList interestingDiff = getInterestingDiff(diff.getEditList());

		final Integer parentLine;
		if (!interestingDiff.isEmpty())
			parentLine = Integer.valueOf(interestingDiff.get(0).getBeginA());
		else
			parentLine = null;

		Composite header = new Composite(diffComposite, SWT.NONE);
		header.setLayout(GridLayoutFactory.fillDefaults().numColumns(2)
				.create());

		Label diffHeaderLabel = new Label(header, SWT.NONE);
		diffHeaderLabel.setText(NLS.bind(
				UIText.BlameInformationControl_DiffHeaderLabel, parentId,
				parentMessage));

		Link showAnnotationsLink = new Link(header, SWT.NONE);
		showAnnotationsLink
				.setText(UIText.BlameInformationControl_ShowAnnotationsLink);
		showAnnotationsLink.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				blameParent(parent, diff, parentLine);
			}
		});

		DiffViewer diffText = new DiffViewer(diffComposite, null, SWT.NONE);
		diffText.setEditable(false);
		diffText.getControl().setLayoutData(
				GridDataFactory.fillDefaults().grab(true, true).create());

		IDocument document = new Document();
		DiffStyleRangeFormatter diffFormatter = new DiffStyleRangeFormatter(
				document);
		diffFormatter.setContext(1);
		diffFormatter.setRepository(revision.getRepository());
		diffFormatter.format(interestingDiff, diff.getOldText(),
				diff.getNewText());

		diffText.setDocument(document);
		diffText.setFormatter(diffFormatter);
	}

	private EditList getInterestingDiff(EditList fullDiff) {
		int hoverLineNumber = getHoverLineNumber();
		Integer sourceLine = revision.getSourceLine(hoverLineNumber);

		if (sourceLine == null)
			return fullDiff;

		int line = sourceLine.intValue();
		EditList interestingDiff = new EditList(1);
		for (Edit edit : fullDiff) {
			if (line >= edit.getBeginB() && line <= edit.getEndB())
				interestingDiff.add(edit);
		}
		return interestingDiff;
	}

	private int getHoverLineNumber() {
		if (hoverInformationControl != null)
			return hoverInformationControl.getHoverLineNumber();
		else
			return revisionRulerLineNumber;
	}

	private void openCommit() {
		try {
			getShell().dispose();
			CommitEditor.open(new RepositoryCommit(revision.getRepository(),
					revision.getCommit()));
		} catch (PartInitException pie) {
			Activator.logError(pie.getLocalizedMessage(), pie);
		}
	}

	private void showCommitInHistory() {
		getShell().dispose();
		IHistoryView part = (IHistoryView) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage()
				.findView(IHistoryView.VIEW_ID);
		if (part == null)
			return;

		Repository repository = revision.getRepository();
		if (!repository.isBare()) {
			String sourcePath = revision.getSourcePath();
			File file = new File(repository.getWorkTree(), sourcePath);
			BlameHistoryPageInput input = new BlameHistoryPageInput(repository,
					revision.getCommit(), file);
			part.showHistoryFor(input);
		} else {
			HistoryPageInput input = new BlameHistoryPageInput(repository,
					revision.getCommit());
			part.showHistoryFor(input);
		}
	}

	private void blameParent(RevCommit parent, Diff diff, Integer sourceLine) {
		try {
			String path = diff.getOldPath();
			IFileRevision rev = CompareUtils.getFileRevision(path, parent,
					revision.getRepository(), null);
			int line = sourceLine == null ? -1 : sourceLine.intValue();
			IStorage storage = rev.getStorage(new NullProgressMonitor());
			IWorkbenchPage page = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage();
			BlameOperation operation = new BlameOperation(
					revision.getRepository(), storage, path, parent,
					getShell(), page, line);
			JobUtil.scheduleUserJob(operation, UIText.ShowBlameHandler_JobName,
					JobFamilies.BLAME);
		} catch (IOException e) {
			Activator.logError(UIText.ShowBlameHandler_errorMessage, e);
		} catch (CoreException e) {
			Activator.logError(UIText.ShowBlameHandler_errorMessage, e);
		}
	}
