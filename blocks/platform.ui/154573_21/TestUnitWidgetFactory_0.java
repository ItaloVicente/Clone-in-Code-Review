
	@Test
	public void setsData() {
		String data = new String("myData");
		Label testLabel = LabelFactory.newLabel(SWT.NONE).data(data).create(shell);

		assertEquals(data, testLabel.getData());
	}
