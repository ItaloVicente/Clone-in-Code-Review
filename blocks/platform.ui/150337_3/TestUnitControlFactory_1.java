
	@Test
	public void setsOrientation() {
		Label label = LabelFactory.newLabel(SWT.NONE).orientation(SWT.LEFT_TO_RIGHT).create(shell);
		assertEquals(SWT.LEFT_TO_RIGHT, label.getOrientation());
	}
