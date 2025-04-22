		TableViewer<Person, List<Person>> viewer = new TableViewer<Person, List<Person>>(
				shell, SWT.BORDER | SWT.FULL_SELECTION);
		viewer.setContentProvider(ArrayContentProvider
				.getInstance(Person.class));

		TableViewerColumn<Person, List<Person>> column = createColumnFor(
				viewer, "Givenname");
    column.setLabelProvider(new ColumnLabelProvider<Person>() {
