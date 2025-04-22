	public void testControlContributionIsSet() {
		ToolBarManager manager = new ToolBarManager();
		manager.add(new ControlContribution("test") {

			@Override
			protected Control createControl(Composite parent) {

				return new Label(parent, SWT.NONE);
			}
		});
		ToolBar toolBar = manager.createControl(createComposite());
		for (ToolItem item : toolBar.getItems()) {
			if (!(item.getData() instanceof ControlContribution)) {
				fail("ToolItem data is not set to ControlContribution");
			}
		}
	}

