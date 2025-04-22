	protected void createEmptyArea(Composite parent) {
		emptyArea = new Composite(parent, SWT.NONE);
		emptyArea.setBackgroundMode(SWT.INHERIT_FORCE);
		GridLayoutFactory.fillDefaults().applyTo(emptyArea);
		Composite infoArea = new Composite(emptyArea, SWT.NONE);
		GridDataFactory.swtDefaults().align(SWT.CENTER, SWT.CENTER)
				.grab(true, true).applyTo(infoArea);
		GridLayoutFactory.swtDefaults().applyTo(infoArea);
		Label messageLabel = new Label(infoArea, SWT.WRAP);
		messageLabel.setText(UIText.RepositoriesView_messsageEmpty);
		GridDataFactory.swtDefaults().align(SWT.FILL, SWT.FILL)
				.grab(true, false).applyTo(messageLabel);
		Composite optionsArea = new Composite(infoArea, SWT.NONE);
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(optionsArea);
		GridDataFactory.swtDefaults().align(SWT.CENTER, SWT.CENTER)
				.grab(true, true).applyTo(optionsArea);

		final FormToolkit toolkit = new FormToolkit(emptyArea.getDisplay());
		emptyArea.addDisposeListener(new DisposeListener() {

			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});
		final Color linkColor = JFaceColors.getHyperlinkText(emptyArea
				.getDisplay());

		Label addLabel = new Label(optionsArea, SWT.NONE);
		addLabel.setImage(UIIcons.CREATE_REPOSITORY.createImage());
		Hyperlink addLink = toolkit.createHyperlink(optionsArea,
				UIText.RepositoriesView_linkAdd, SWT.WRAP);
		addLink.setForeground(linkColor);
		addLink.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
				IHandlerService service = (IHandlerService) getViewSite()
						.getService(IHandlerService.class);
				UIUtils.executeCommand(service,
						"org.eclipse.egit.ui.RepositoriesViewAddRepository"); //$NON-NLS-1$
			}
		});
		GridDataFactory.swtDefaults().align(SWT.FILL, SWT.FILL)
				.grab(true, false).applyTo(addLink);

		Label cloneLabel = new Label(optionsArea, SWT.NONE);
		cloneLabel.setImage(UIIcons.CLONEGIT.createImage());
		Hyperlink cloneLink = toolkit.createHyperlink(optionsArea,
				UIText.RepositoriesView_linkClone, SWT.WRAP);
		cloneLink.setForeground(linkColor);
		cloneLink.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
				IHandlerService service = (IHandlerService) getViewSite()
						.getService(IHandlerService.class);
				UIUtils.executeCommand(service,
						"org.eclipse.egit.ui.RepositoriesViewClone"); //$NON-NLS-1$
			}
		});
		GridDataFactory.swtDefaults().align(SWT.FILL, SWT.FILL)
				.grab(true, false).applyTo(cloneLink);

		Label createLabel = new Label(optionsArea, SWT.NONE);
		createLabel.setImage(UIIcons.NEW_REPOSITORY.createImage());
		Hyperlink createLink = toolkit.createHyperlink(optionsArea,
				UIText.RepositoriesView_linkCreate, SWT.WRAP);
		createLink.setForeground(linkColor);
		createLink.setText(UIText.RepositoriesView_linkCreate);
		createLink.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
				IHandlerService service = (IHandlerService) getViewSite()
						.getService(IHandlerService.class);
				UIUtils.executeCommand(service,
						"org.eclipse.egit.ui.RepositoriesViewCreateRepository"); //$NON-NLS-1$
			}
		});
		GridDataFactory.swtDefaults().align(SWT.FILL, SWT.FILL)
				.grab(true, false).applyTo(createLink);
	}

