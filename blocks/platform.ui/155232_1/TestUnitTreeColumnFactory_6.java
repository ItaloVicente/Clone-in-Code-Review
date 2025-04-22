	}

	@Test
	public void setsWidth() {
		TreeColumn treeColumn = TreeColumnFactory.newTreeColumn(SWT.NONE).width(20).create(tree);

		assertEquals(20, treeColumn.getWidth());
	}

	@Test
	public void setsMoveable() {
		TreeColumn treeColumn = TreeColumnFactory.newTreeColumn(SWT.NONE).moveable(true).create(tree);

		assertTrue(treeColumn.getMoveable());
	}

	@Test
	public void setsResizable() {
		TreeColumn treeColumn = TreeColumnFactory.newTreeColumn(SWT.NONE).resizable(true).create(tree);

		assertTrue(treeColumn.getResizable());
