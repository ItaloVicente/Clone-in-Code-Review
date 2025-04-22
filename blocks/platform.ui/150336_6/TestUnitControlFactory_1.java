	}

	@Test
	public void createsControlWithProperties() {
		Font font = new Font(null, new FontData());

		Label label = LabelFactory.newLabel(SWT.NONE).font(font).create(shell);
