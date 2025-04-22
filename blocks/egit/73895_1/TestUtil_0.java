	public static SWTBotTreeItem expandAndWaitFor(final SWTBotTreeItem treeItem,
			final String childName) {
		final String text = treeItem.getText();
		final SWTBotTreeItem[] result = { null };
		treeItem.expand();
		new SWTBot().waitUntil(new DefaultCondition() {

			@Override
			public boolean test() {
				if (treeItem.widget.isDisposed()) {
					return true; // Stop waiting and report failure below.
				}
				try {
					result[0] = treeItem.getNode(childName);
					return true;
				} catch (WidgetNotFoundException e) {
					return false;
				}
			}

			@Override
			public String getFailureMessage() {
				return "Child " + childName + " not found under " + text;
			}
		});
		if (treeItem.widget.isDisposed()) {
			fail("Widget disposed while waiting for child node " + text + '.'
					+ childName);
		}
		return result[0];
	}

