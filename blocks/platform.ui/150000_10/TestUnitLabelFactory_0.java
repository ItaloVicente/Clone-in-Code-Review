
	@Test
	public void createsControlWithNullsWhenNothingSet() {
		Label label = LabelFactory.newLabel(SWT.NONE).create(shell);

		assertTrue(label.getEnabled());
		assertNull(label.getLayoutData());
		assertNull(label.getToolTipText());

		assertEquals(label, shell.getChildren()[0]);
	}

	@Test
	public void createsDifferentControlsWithSameFactory() {
		LabelFactory labelFactory = LabelFactory.newLabel(SWT.NONE);

		Label label1 = labelFactory.create(shell);
		Label label2 = labelFactory.create(shell);

		assertNotSame(label1, label2);
		assertEquals(label1, shell.getChildren()[0]);
		assertEquals(label2, shell.getChildren()[1]);
	}

	@Test
	public void createsControlWithProperties() {
		Font font = new Font(null, new FontData());

		Label label = LabelFactory.newLabel(SWT.NONE) //
				.tooltip("toolTip") //
				.enabled(false) //
				.layoutData(new GridData(GridData.FILL_BOTH)) //
				.font(font) //
				.create(shell);

		assertFalse(label.getEnabled());
		assertEquals("toolTip", label.getToolTipText());
		assertTrue(label.getLayoutData() instanceof GridData);
		assertEquals(font, label.getFont());
	}

	@Test
	public void testUniqueLayoutData() {
		GridDataFactory gridDataFactory = GridDataFactory.fillDefaults().grab(true, false);
		LabelFactory labelFactory = LabelFactory.newLabel(SWT.NONE).layoutData(gridDataFactory::create);

		Label label = labelFactory.create(shell);
		Label label2 = labelFactory.create(shell);

		assertNotSame(label.getLayoutData(), label2.getLayoutData());
	}
