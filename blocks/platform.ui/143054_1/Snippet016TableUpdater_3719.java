		Table t = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION | SWT.VIRTUAL);
		t.setHeaderVisible(true);
		createColumn(t, "Values");
		t.setLinesVisible(true);
		final WritableList<Stuff> list = new WritableList<>();
		new TableUpdater<Stuff>(t, list) {
