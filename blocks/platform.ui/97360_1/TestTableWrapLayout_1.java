	private boolean runAlignmentTest(Control control, int alignment) {
		TableWrapData dataLeft = new TableWrapData();
		dataLeft.align = alignment;
		dataLeft.grabHorizontal = true;
		control.setLayoutData(dataLeft);

		inner.setSize(1000, 1000);
		inner.layout(false);
		return control.getSize().x == 1000;
	}

	@Test
	public void testLeftAlignmentIsIgnoredForWrappingControls() {
		Label label = new Label(inner, SWT.WRAP);
		label.setText("test");

		assertEquals(true, runAlignmentTest(label, TableWrapData.LEFT));
	}

	@Test
	public void testLeftAlignmentIsRespectedForNonWrappingControls() {
		Label label = new Label(inner, SWT.NONE);
		label.setText("test");

		assertEquals(false, runAlignmentTest(label, TableWrapData.LEFT));
	}

	@Test
	public void testLeftAlignmentIsIgnoredForLayoutsImplementingLayoutExtension() {
		Control label = ControlFactory.create(inner, 10, 200, 100);

		assertEquals(true, runAlignmentTest(label, TableWrapData.LEFT));
	}

	@Test
	public void testRightAlignmentIsIgnoredForWrappingControls() {
		Label label = new Label(inner, SWT.WRAP);
		label.setText("test");

		assertEquals(true, runAlignmentTest(label, TableWrapData.RIGHT));
	}

	@Test
	public void testRightAlignmentIsRespectedForNonWrappingControls() {
		Label label = new Label(inner, SWT.NONE);
		label.setText("test");

		assertEquals(false, runAlignmentTest(label, TableWrapData.RIGHT));
	}

	@Test
	public void testRightAlignmentIsIgnoredForLayoutsImplementingLayoutExtension() {
		Control label = ControlFactory.create(inner, 10, 200, 100);

		assertEquals(true, runAlignmentTest(label, TableWrapData.RIGHT));
	}

	@Test
	public void testCenterAlignmentIsIgnoredForWrappingControls() {
		Label label = new Label(inner, SWT.WRAP);
		label.setText("test");

		assertEquals(true, runAlignmentTest(label, TableWrapData.CENTER));
	}

	@Test
	public void testCenterAlignmentIsRespectedForNonWrappingControls() {
		Label label = new Label(inner, SWT.NONE);
		label.setText("test");

		assertEquals(false, runAlignmentTest(label, TableWrapData.CENTER));
	}

	@Test
	public void testCenterAlignmentIsIgnoredForLayoutsImplementingLayoutExtension() {
		Control label = ControlFactory.create(inner, 10, 200, 100);

		assertEquals(true, runAlignmentTest(label, TableWrapData.CENTER));
	}

