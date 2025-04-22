
		ControlContribution repositoryLabelControl = new ControlContribution("repositoryLabel") { //$NON-NLS-1$
			@Override
			protected Control createControl(Composite parent) {
				FormToolkit toolkit = getHeaderForm().getToolkit();
				Composite composite = toolkit.createComposite(parent);
				RowLayout layout = new RowLayout();
				composite.setLayout(layout);
				composite.setBackground(null);
				String label = getCommit().getRepositoryName();

				ImageHyperlink link = new ImageHyperlink(composite, SWT.NONE);
				link.setText(label);
				link.setFont(JFaceResources.getBannerFont());
				link.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
				link.setToolTipText(UIText.CommitEditor_showGitRepo);
				link.addHyperlinkListener(new HyperlinkAdapter() {
					@Override
					public void linkActivated(HyperlinkEvent event) {
						RepositoriesView view;
						try {
							view = (RepositoriesView) PlatformUI.getWorkbench()
									.getActiveWorkbenchWindow().getActivePage().showView(
											RepositoriesView.VIEW_ID);
							view.showRepository(getCommit().getRepository());
						} catch (PartInitException e) {
							Activator.handleError(UIText.CommitEditor_couldNotShowRepository, e, false);
						}
					}
				});

				return composite;
			}
		};
		toolbar.add(repositoryLabelControl);
