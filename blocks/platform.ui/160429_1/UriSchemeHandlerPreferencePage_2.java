	private void createLoadingTextControls(Composite parent) {
		loadingTextComposite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(loadingTextComposite);
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.CENTER, SWT.TOP).applyTo(loadingTextComposite);

		WidgetFactory.label(SWT.NONE).text("Loading Link Hander registration...").create(loadingTextComposite); //$NON-NLS-1$

		if (operatingSystemRegistration != null) {
			loadingTextComposite.setVisible(false); // set invisible once loaded
		}
	}

