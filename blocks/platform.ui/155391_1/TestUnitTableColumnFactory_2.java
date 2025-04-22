	}

	@Test
	public void setsToolTip() {
		TableColumn tableColumn = TableColumnFactory.newTableColumn(SWT.NONE).tooltip("tooltip").create(table);

