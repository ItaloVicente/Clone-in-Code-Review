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
	}

	@Test
	public void testHorizontalSpacing() {
		layout.horizontalSpacing = 1000;
		ControlFactory.create(inner, 20, 20, 30);
		Composite secondControl = ControlFactory.create(inner, 20, 20, 30);
		Point size = inner.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		assertEquals(1040, size.x);
		inner.pack(true);
		assertEquals(new Rectangle(1020, 0, 20, 30), secondControl.getBounds());
	}

	@Test
	public void testHorizontalMargins() {
		layout.leftMargin = 100;
		layout.rightMargin = 10;
		Control leftControl = ControlFactory.create(inner, 20, 20, 30);
		Control rightControl = ControlFactory.create(inner, 20, 20, 40);
		Point size = inner.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		assertEquals(150, size.x);
		inner.pack(true);
		assertEquals(new Rectangle(100, 0, 20, 30), leftControl.getBounds());
		assertEquals(new Rectangle(120, 0, 20, 40), rightControl.getBounds());
		assertEquals(new Rectangle(0, 0, 150, 40), inner.getBounds());
	}

	@Test
	public void testVerticalSpacingHasNoEffectWhenOnlyOneColumn() {
		layout.verticalSpacing = 1000;
		Composite control = ControlFactory.create(inner, 20, 20, 30);
		Point size = inner.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		assertEquals(20, size.x);
		inner.pack(true);
		assertEquals(new Rectangle(0, 0, 20, 30), control.getBounds());
	}

	@Test
	public void testVerticalSpacing() {
		layout.verticalSpacing = 1000;
		layout.maxNumColumns = 1;
		ControlFactory.create(inner, 20, 20, 30);
		Composite secondControl = ControlFactory.create(inner, 20, 20, 30);
		Point size = inner.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		assertEquals(1060, size.y);
		inner.pack(true);
		assertEquals(new Rectangle(0, 1030, 20, 30), secondControl.getBounds());
	}

	@Test
	public void testVerticalMargins() {
		layout.topMargin = 100;
		layout.bottomMargin = 10;
		layout.maxNumColumns = 1;
		Control control1 = ControlFactory.create(inner, 20, 20, 30);
		Control control2 = ControlFactory.create(inner, 20, 20, 40);
		Point size = inner.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		assertEquals(180, size.y);
		inner.pack();
		assertEquals(new Rectangle(0, 100, 20, 30), control1.getBounds());
		assertEquals(new Rectangle(0, 130, 20, 40), control2.getBounds());
		assertEquals(new Rectangle(0, 0, 20, 180), inner.getBounds());
	}

	@Test
	public void testSelectsCorrectNumberOfColumns() {
		layout.horizontalSpacing = 10;
		layout.leftMargin = 10;
		layout.rightMargin = 10;

		ControlFactory.create(inner, 21, 30, 50);
		ControlFactory.create(inner, 22, 40, 50);
		ControlFactory.create(inner, 23, 50, 50);

		Point size = inner.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		assertEquals(190, size.x);
		assertEquals(50, size.y);

		size = inner.computeSize(109, SWT.DEFAULT);
		assertEquals(109, size.x);
		assertEquals(108, size.y);

		inner.pack(true);
		assertAllChildrenHaveWidth(50);

		for (Control next : inner.getChildren()) {
			assertEquals(0, next.getBounds().y);
		}

		size = inner.computeSize(108, SWT.DEFAULT);
		assertEquals(108, size.x);
		assertEquals(89, size.y);

		int minWidth = layout.computeMinimumWidth(inner, false);
		assertEquals(43, minWidth);
	}

	@Test
	public void testFillAlignment() {
		layout.maxNumColumns = 1;

		Composite control1 = ControlFactory.create(inner, 100, 800, 200);
		ColumnLayoutData data1 = new ColumnLayoutData();
		data1.widthHint = 400;
		control1.setLayoutData(data1);

		Composite control2 = ControlFactory.create(inner, 50, 100, 1200);
		ColumnLayoutData data2 = new ColumnLayoutData();
		data2.widthHint = 200;
		control2.setLayoutData(data2);

		Point size = inner.computeSize(SWT.DEFAULT, SWT.DEFAULT);

		assertEquals(400, size.x);

		assertEquals(700, size.y);

		inner.pack();

		assertEquals(new Rectangle(0, 0, 400, 400), control1.getBounds());
		assertEquals(new Rectangle(0, 400, 400, 300), control2.getBounds());
	}

	@Test
	public void testLeftAlignment() {
		layout.maxNumColumns = 1;

		Composite control1 = ControlFactory.create(inner, 200, 200, 200);

		Composite control2 = ControlFactory.create(inner, 10, 100, 100);
		ColumnLayoutData data2 = new ColumnLayoutData();
		data2.horizontalAlignment = ColumnLayoutData.LEFT;
		data2.widthHint = 50;
		control2.setLayoutData(data2);

		Composite control3 = ControlFactory.create(inner, 10, 50, 100);
		ColumnLayoutData data3 = new ColumnLayoutData();
		data3.widthHint = 10;
		data3.horizontalAlignment = ColumnLayoutData.LEFT;
		control3.setLayoutData(data3);

		Point size = inner.computeSize(SWT.DEFAULT, SWT.DEFAULT);

		assertEquals(200, size.x);
		assertEquals(900, size.y);

		inner.pack(true);

		assertEquals(new Rectangle(0, 0, 200, 200), control1.getBounds());
		assertEquals(new Rectangle(0, 200, 50, 200), control2.getBounds());
		assertEquals(new Rectangle(0, 400, 10, 500), control3.getBounds());


		size = inner.computeSize(25, SWT.DEFAULT);

		assertEquals(25, size.x);
		assertEquals(2500, size.y);
	}

	@Test
	public void testControlsFlushedCorrectly()
	{
		Composite composite = ControlFactory.create(inner, 200, 200, 200);
		TestLayout layout = (TestLayout) composite.getLayout();

		inner.computeSize(SWT.DEFAULT, SWT.DEFAULT, false);
		layout.wasChanged = false;

		inner.computeSize(SWT.DEFAULT, SWT.DEFAULT, false);
		assertEquals(false, layout.wasChanged);

		inner.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
		assertEquals(true, layout.wasChanged);

		layout.wasChanged = false;
		inner.layout(false);
		assertEquals(false, layout.wasChanged);

		inner.layout(true);
		assertEquals(true, layout.wasChanged);
