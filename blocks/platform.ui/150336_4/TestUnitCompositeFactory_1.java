
	@Test
	public void setsForeground() {
		Color color = new Color(null, 255, 255, 255);
		Composite composite = CompositeFactory.newComposite(SWT.NONE).foreground(color).create(shell);

		assertEquals(color, composite.getForeground());
	}

	@Test
	public void setsBackground() {
		Color color = new Color(null, 0, 0, 0);
		Composite composite = CompositeFactory.newComposite(SWT.NONE).background(color).create(shell);

		assertEquals(color, composite.getBackground());
	}
