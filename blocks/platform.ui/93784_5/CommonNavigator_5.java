	private Composite createContentComposite(Composite parent) {
		Composite content = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		content.setLayout(layout);
		return content;
	}

	private Composite createCommonViewerComposite(Composite parent) {
		Composite commonViewerParent = new Composite(parent, SWT.NONE);
		commonViewerParent.setLayout(new FillLayout());
		GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
		commonViewerParent.setLayoutData(layoutData);
		return commonViewerParent;
	}

