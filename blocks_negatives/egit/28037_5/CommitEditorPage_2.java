		if (count > 0) {
			Composite parents = toolkit.createComposite(top);
			GridLayoutFactory.fillDefaults().spacing(2, 2).numColumns(2)
					.applyTo(parents);
			GridDataFactory.fillDefaults().grab(false, false).applyTo(parents);

			for (int i = 0; i < count; i++) {
				final RevCommit parentCommit = commit.getParent(i);
				toolkit.createLabel(parents,
						UIText.CommitEditorPage_LabelParent).setForeground(
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
						}
