
	@Test
	public void setsMoveable() {
		TableColumn column = TableColumnFactory.newTableColumn(SWT.NONE).moveable(true).create(table);

		assertTrue(column.getMoveable());
	}

	@Test
	public void setsResizable() {
		TableColumn column = TableColumnFactory.newTableColumn(SWT.NONE).resizable(true).create(table);

		assertTrue(column.getResizable());
	}
