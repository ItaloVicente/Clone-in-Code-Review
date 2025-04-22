		FetchResultTable table = new FetchResultTable(composite);
		GridDataFactory.fillDefaults().grab(true, true).hint(600, 300)
				.applyTo(table.getControl());
		if (result.getFetchResult() != null) {
			table.setData(localDb, result.getFetchResult());
		}
