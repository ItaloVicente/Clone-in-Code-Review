		layout.numColumns = 2;
		Control l1 = ControlFactory.create(inner, 31, 100, 15);
		Control l2 = ControlFactory.create(inner, 32, 200, 15);
		Control l3 = ControlFactory.create(inner, 33, 400, 15);
		Control l4 = ControlFactory.create(inner, 34, 800, 15);

		inner.setSize(300, 1000);
		inner.layout(false);

		assertEquals(300, l3.getBounds().width + l4.getBounds().width);
		assertTrue(rightEdge(l1) <= l2.getBounds().x);
		assertEquals(rightEdge(l3), l4.getBounds().x);
		assertTrue(bottomEdge(l1) <= l3.getBounds().y);
		assertTrue(bottomEdge(l1) <= l4.getBounds().y);

		Point preferredSize = inner.computeSize(SWT.DEFAULT, SWT.DEFAULT, false);
		assertEquals(new Point(1200, 30), preferredSize);

		int minWidth = layout.computeMinimumWidth(inner, false);
		assertEquals(67, minWidth);
