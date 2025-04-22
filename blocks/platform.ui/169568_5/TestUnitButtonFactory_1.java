	@Test(expected = UnsupportedOperationException.class)
	public void createsButtonWithoutConfiguredParent() {
		ButtonFactory.newButton(SWT.PUSH).create();
	}

	public void createsButtonWithConfiguredParent() {
		ButtonFactory.newButton(SWT.PUSH).parent(shell).create();
	}

