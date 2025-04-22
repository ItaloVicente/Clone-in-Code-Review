		layout.leftMargin = 5;
		layout.rightMargin = 5;
		ControlFactory.create(inner, 20, 20, 30);
		ControlFactory.create(inner, 20, 20, 40);
		ControlFactory.create(inner, 20, 20, 20);
		Point size = inner.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		assertEquals(70, size.y);
		inner.setSize(size);
		inner.layout(true);
		assertEquals(new Rectangle(5, 2, 20, 30), inner.getChildren()[0].getBounds());
		assertEquals(new Rectangle(30, 2, 20, 40), inner.getChildren()[1].getBounds());
	}

	@Test
	public void testHorizontalSpacingHasNoEffectWhenOnlyOneColumn() {
		layout.horizontalSpacing = 1000;
		Composite control = ControlFactory.create(inner, 20, 20, 30);
		Point size = inner.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		assertEquals(20, size.x);
		inner.pack(true);
		assertEquals(new Rectangle(0, 0, 20, 30), control.getBounds());
