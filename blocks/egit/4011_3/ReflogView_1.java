	private void updateRefLink(final String name) {
		IToolBarManager toolbar = form.getToolBarManager();
		toolbar.removeAll();

		ControlContribution refLabelControl = new ControlContribution(
				"refLabel") { //$NON-NLS-1$
			@Override
			protected Control createControl(Composite cParent) {
				Composite composite = toolkit.createComposite(cParent);
				composite.setLayout(new RowLayout());
				composite.setBackground(null);

				final ImageHyperlink refLink = new ImageHyperlink(composite,
						SWT.NONE);
				Image image = UIIcons.BRANCH.createImage();
				UIUtils.hookDisposal(refLink, image);
				refLink.setImage(image);
				refLink.setFont(JFaceResources.getBannerFont());
				refLink.setForeground(toolkit.getColors().getColor(
						IFormColors.TITLE));
				refLink.addHyperlinkListener(new HyperlinkAdapter() {
					@Override
					public void linkActivated(HyperlinkEvent event) {
						Repository repository = getRepository();
						if (repository == null)
							return;
						RefSelectionDialog dialog = new RefSelectionDialog(
								refLink.getShell(), repository);
						if (Window.OK == dialog.open())
							showReflogFor(repository, dialog.getRefName());
					}
				});
				refLink.setText(Repository.shortenRefName(name));

				return composite;
			}
		};
		toolbar.add(refLabelControl);
		toolbar.update(true);
	}

	private Repository getRepository() {
		Object input = refLogTableTreeViewer.getInput();
		if (input instanceof ReflogInput)
			return ((ReflogInput) input).getRepository();
		return null;
	}

