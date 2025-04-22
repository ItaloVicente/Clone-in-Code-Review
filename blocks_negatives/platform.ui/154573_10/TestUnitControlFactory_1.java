	@Test
	public void createsDifferentControlsWithSameFactory() {
		LabelFactory testFactory = LabelFactory.newLabel(SWT.NONE);

		Label label1 = testFactory.create(shell);
		Label label2 = testFactory.create(shell);

		assertNotSame(label1, label2);
		assertEquals(label1, shell.getChildren()[0]);
		assertEquals(label2, shell.getChildren()[1]);
	}

