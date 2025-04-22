	protected void createEmptyArea(Composite parent) {
		emptyArea = new Composite(parent, SWT.NONE);
		emptyArea.setBackgroundMode(SWT.INHERIT_FORCE);
		GridLayoutFactory.fillDefaults().applyTo(emptyArea);
		Composite infoArea = new Composite(emptyArea, SWT.NONE);
		GridDataFactory.swtDefaults().align(SWT.CENTER, SWT.CENTER)
				.grab(true, true).applyTo(infoArea);
		GridLayoutFactory.swtDefaults().applyTo(infoArea);
		Label messageLabel = new Label(infoArea, SWT.NONE);
		messageLabel.setText(UIText.RepositoriesView_messsageEmpty);
		Composite optionsArea = new Composite(infoArea, SWT.NONE);
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(optionsArea);
		GridDataFactory.swtDefaults().align(SWT.CENTER, SWT.CENTER)
				.grab(true, true).applyTo(optionsArea);

		Label addLabel = new Label(optionsArea, SWT.NONE);
		addLabel.setImage(UIIcons.CREATE_REPOSITORY.createImage());
		Link addLink = new Link(optionsArea, SWT.NONE);
		addLink.setText(UIText.RepositoriesView_linkAdd);
		addLink.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				IHandlerService service = (IHandlerService) getViewSite()
						.getService(IHandlerService.class);
				UIUtils.executeCommand(service,
						"org.eclipse.egit.ui.RepositoriesViewAddRepository"); //$NON-NLS-1$
			}
		});

		Label cloneLabel = new Label(optionsArea, SWT.NONE);
		cloneLabel.setImage(UIIcons.CLONEGIT.createImage());
		Link cloneLink = new Link(optionsArea, SWT.NONE);
		cloneLink.setText(UIText.RepositoriesView_linkClone);
		cloneLink.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				IHandlerService service = (IHandlerService) getViewSite()
						.getService(IHandlerService.class);
				UIUtils.executeCommand(service,
						"org.eclipse.egit.ui.RepositoriesViewClone"); //$NON-NLS-1$
			}
		});

		Label createLabel = new Label(optionsArea, SWT.NONE);
		createLabel.setImage(UIIcons.NEW_REPOSITORY.createImage());
		Link createLink = new Link(optionsArea, SWT.NONE);
		createLink.setText(UIText.RepositoriesView_linkCreate);
		createLink.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				IHandlerService service = (IHandlerService) getViewSite()
						.getService(IHandlerService.class);
				UIUtils.executeCommand(service,
						"org.eclipse.egit.ui.RepositoriesViewCreateRepository"); //$NON-NLS-1$
			}
		});
	}

