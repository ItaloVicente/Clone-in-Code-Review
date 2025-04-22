	/**
	 * Create fetch result table under given parent composite
	 *
	 * @param parent
	 * @return main result table control
	 */
	public Control createFetchResultTable(Composite parent) {
		final FetchResultTable table = new FetchResultTable(parent);
		if (result.getFetchResult() != null)
			table.setData(localDb, result.getFetchResult());
		final Control tableControl = table.getControl();
		GridDataFactory.fillDefaults().grab(true, true).hint(600, 300)
				.applyTo(tableControl);
		return table.getControl();
	}

