		assertEquals(15, l1.getSize().y);

		inner.setSize(100, 300);
		inner.layout();
		assertEquals(15, l1.getSize().y);

		inner.setSize(50, 300);
		inner.layout();
		assertEquals(30, l1.getSize().y);

		assertEquals(30, layout.computeMinimumWidth(inner, false));
		assertEquals(100, layout.computeMaximumWidth(inner, false));
