
	@Test
	public void createsDifferentItemsWithSameFactory() {
		TableColumnFactory tableColumnFactory = TableColumnFactory.newTableColumn(SWT.CENTER);

		TableColumn column1 = tableColumnFactory.create(table);
		TableColumn column2 = tableColumnFactory.create(table);

		assertNotSame(column1, column2);
		assertEquals(column1, table.getColumn(0));
		assertEquals(column2, table.getColumn(1));
	}

	@Test
	public void createsControlWithProperties() {
		TableColumn column = TableColumnFactory.newTableColumn(SWT.CENTER).image(image).text("Column").create(table);

		assertEquals(image, column.getImage());
		assertEquals("Column", column.getText());
	}
