		Control l1 = ControlFactory.create(inner, 10, 100, 15);
		Control l2 = ControlFactory.create(inner, 80, 800, 15);

		Point preferredSize = inner.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		int minimumWidth = layout.computeMinimumWidth(inner, false);
		Point wrappedSize = inner.computeSize(400, SWT.DEFAULT);

		inner.pack();
		assertEquals(new Rectangle(0, 0, 100, 15), l1.getBounds());
		assertEquals(new Rectangle(0, 15, 800, 15), l2.getBounds());
		assertEquals(new Point(800, 30), preferredSize);
		assertEquals(80, minimumWidth);
		assertEquals(new Point(400, 45), wrappedSize);
