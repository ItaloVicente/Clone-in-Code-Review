		if (count > 0)
			createParentsArea(top, toolkit, commit);

		createTagsArea(userArea, toolkit, 2);
	}

	private void createParentsArea(Composite parent, FormToolkit toolkit,
			RevCommit commit) {
		Composite parents = toolkit.createComposite(parent);
		GridLayoutFactory.fillDefaults().spacing(2, 2).numColumns(2)
				.applyTo(parents);
		GridDataFactory.fillDefaults().grab(false, false).applyTo(parents);

		for (int i = 0; i < commit.getParentCount(); i++) {
			final RevCommit parentCommit = commit.getParent(i);
			toolkit.createLabel(parents, getParentCommitLabel(i))
					.setForeground(
							toolkit.getColors().getColor(IFormColors.TB_TOGGLE));
			final Hyperlink link = toolkit
					.createHyperlink(parents,
							parentCommit.abbreviate(PARENT_LENGTH).name(),
							SWT.NONE);
			link.addHyperlinkListener(new HyperlinkAdapter() {
				public void linkActivated(HyperlinkEvent e) {
					try {
						CommitEditor.open(new RepositoryCommit(getCommit()
								.getRepository(), parentCommit));
						if ((e.getStateMask() & SWT.MOD1) != 0)
							getEditor().close(false);
					} catch (PartInitException e1) {
						Activator.logError(
								"Error opening commit editor", e1);//$NON-NLS-1$
