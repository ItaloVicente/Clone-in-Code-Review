
	@Test
	public void setOrientation() {
		Text text = TextFactory.newText(SWT.NONE).orientation(SWT.LEFT_TO_RIGHT).create(shell);
		assertEquals(SWT.LEFT_TO_RIGHT, text.getOrientation());
	}
