
	@Test
	public void setsForeground() {
		Color color = new Color(null, 255, 255, 255);
		Label label = LabelFactory.newLabel(SWT.NONE).foreground(color).create(shell);

		assertEquals(color, label.getForeground());
	}

	@Test
	public void setsBackground() {
		Color color = new Color(null, 0, 0, 0);
		Label label = LabelFactory.newLabel(SWT.NONE).background(color).create(shell);

		assertEquals(color, label.getBackground());
	}

	@Test
	public void setsOrientation() {
		Label label = LabelFactory.newLabel(SWT.NONE).orientation(SWT.LEFT_TO_RIGHT).create(shell);
		assertEquals(SWT.LEFT_TO_RIGHT, label.getOrientation());
	}
