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
					}
				});
