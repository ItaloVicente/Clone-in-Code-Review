
	public void testGridDataCreate() {
		GridData actual = GridDataFactory.create(GridData.FILL_HORIZONTAL).create();
		GridData expected = new GridData(GridData.FILL_HORIZONTAL);
		assertEquals(expected.toString(), actual.toString());
	}
