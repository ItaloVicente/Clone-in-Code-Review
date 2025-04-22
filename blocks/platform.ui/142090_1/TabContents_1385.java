		return null;
	}

	public ISection[] getSections() {
		return sections;
	}

	public void createControls(Composite parent,
			final TabbedPropertySheetPage page) {
		Composite pageComposite = page.getWidgetFactory().createComposite(
			parent, SWT.NO_FOCUS);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.verticalSpacing = 0;
		pageComposite.setLayout(layout);

		for (final ISection section : sections) {
			final Composite sectionComposite = page.getWidgetFactory()
				.createComposite(pageComposite, SWT.NO_FOCUS);
			sectionComposite.setLayout(new FillLayout());
			int style = (section.shouldUseExtraSpace()) ? GridData.FILL_BOTH
				: GridData.FILL_HORIZONTAL;
			GridData data = new GridData(style);
			data.heightHint = section.getMinimumHeight();
			sectionComposite.setLayoutData(data);

			ISafeRunnable runnable = new SafeRunnable() {

				@Override
