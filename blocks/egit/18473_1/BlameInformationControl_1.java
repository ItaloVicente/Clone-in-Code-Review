
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
			}
		}
	}

	private void createDiffLinkAndText(final RevCommit parent, final Diff diff)
			throws IOException {
		String parentName = parent.toObjectId().abbreviate(7).name();

		EditList interestingDiff = getInterestingDiff(diff.getEditList());

		final Integer parentLine;
		if (!interestingDiff.isEmpty())
			parentLine = Integer.valueOf(interestingDiff.get(0).getBeginA());
		else
			parentLine = null;

		Link link = new Link(diffComposite, SWT.NONE);
		link.setText(NLS.bind(UIText.BlameInformationControl_DiffLinkText,
				parentName));
		link.addSelectionListener(new SelectionAdapter() {
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
					revision.getRepository(), storage, path,
					parent.toObjectId(), getShell(), page, line);
			JobUtil.scheduleUserJob(operation, UIText.ShowBlameHandler_JobName,
					JobFamilies.BLAME);
		} catch (IOException e) {
			Activator.logError(UIText.ShowBlameHandler_errorMessage, e);
		} catch (CoreException e) {
			Activator.logError(UIText.ShowBlameHandler_errorMessage, e);
		}
	}
