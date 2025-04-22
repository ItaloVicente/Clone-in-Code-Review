	@Test
	public void testWrappingPoint() {
		Control l1 = ControlFactory.create(inner, 30, 100, 15);

		Point preferredSize = inner.computeSize(SWT.DEFAULT, SWT.DEFAULT, false);
		int preferredHeightWheneThereIsExtraHorizontalSpace = inner.computeSize(200, SWT.DEFAULT).y;
		int preferredHeightWhenControlFillsSpace = inner.computeSize(100, SWT.DEFAULT).y;
		int preferredHeightWhenControlCompressed = inner.computeSize(50, SWT.DEFAULT).y;
		assertEquals(15, preferredHeightWheneThereIsExtraHorizontalSpace);
		assertEquals(15, preferredHeightWhenControlFillsSpace);
		assertEquals(30, preferredHeightWhenControlCompressed);
		assertEquals(new Point(100, 15), preferredSize);

		inner.setSize(100, 15);
