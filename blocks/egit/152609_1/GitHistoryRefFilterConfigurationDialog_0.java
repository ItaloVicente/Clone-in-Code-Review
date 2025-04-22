	private void createFiltersComposite(Composite parent) {
		Group filtersComposite = new Group(parent, SWT.NONE);
		filtersComposite.setText(
				UIText.GitHistoryPage_filterRefDialog_filtersCompositLabel);

		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false)
				.applyTo(filtersComposite);
		GridDataFactory.fillDefaults().grab(true, true)
				.applyTo(filtersComposite);
		filtersComposite.setBackground(parent.getBackground());

