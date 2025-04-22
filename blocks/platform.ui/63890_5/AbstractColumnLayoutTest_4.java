
	public void testComputeSize() {
		Composite composite = new Composite(shell, SWT.NONE);
		TableColumnLayout layout = new TableColumnLayout();
		composite.setLayout(layout);

		Table table = new Table(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		TableColumn col1 = new TableColumn(table, SWT.LEFT);
		TableColumn col2 = new TableColumn(table, SWT.LEFT);

		layout.setColumnData(col1, new ColumnWeightData(1, 40));
		layout.setColumnData(col2, new ColumnWeightData(1, 200));

		shell.pack();
		shell.open();

		assertTrue(col1.getWidth() >= 40);
		assertTrue(col2.getWidth() >= 200);

		int width1 = col1.getWidth();
		int width2 = col2.getWidth();
		shell.pack();
		assertEquals(width1, col1.getWidth());
		assertEquals(width2, col2.getWidth());

	}
