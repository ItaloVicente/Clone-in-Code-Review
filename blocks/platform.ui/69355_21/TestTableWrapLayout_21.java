	@Test
	public void testTableWrapLayoutWrappingLabels() {
		Control l1 = ControlFactory.create(inner, 30, 100, 15);
		Control l2 = ControlFactory.create(inner, 50, 800, 15);

		inner.setSize(300, 1000);
		inner.layout(false);

		assertEquals("l1 had the wrong bounds", new Rectangle(0, 0, 100, 15), l1.getBounds());
		assertEquals("l2 had the wrong bounds", new Rectangle(0, 15, 300, 40), l2.getBounds());
