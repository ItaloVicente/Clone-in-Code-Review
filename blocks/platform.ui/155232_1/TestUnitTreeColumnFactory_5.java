	}

	@Test
	public void setsToolTip() {
		TreeColumn treeColumn = TreeColumnFactory.newTreeColumn(SWT.NONE).tooltip("tooltip").create(tree);

