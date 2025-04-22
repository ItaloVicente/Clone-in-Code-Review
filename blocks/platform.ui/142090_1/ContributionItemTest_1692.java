	public void testForceModeText() {
		Action action = new DummyAction();
		action.setImageDescriptor(
		AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipse.ui.tests",
				"icons/anything.gif"));
		ActionContributionItem item = new ActionContributionItem(action);
		item.fill(getShell());
