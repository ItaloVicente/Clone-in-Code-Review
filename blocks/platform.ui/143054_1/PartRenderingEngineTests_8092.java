		CTabFolder folder = (CTabFolder) stack.getWidget();
		Composite compA = (Composite) folder.getTopRight();
		ToolBar toolbar = null;
		for (Control child : compA.getChildren()) {
			if (child.getData().equals(StackRenderer.TAG_VIEW_MENU)) {
				toolbar = (ToolBar) child;
			}
		}
		assertTrue(toolbar != null);

		assertFalse(toolbar.getVisible());

		MDirectMenuItem item = ems.createModelElement(MDirectMenuItem.class);
		menu.getChildren().add(item);
		contextRule.spinEventLoop();

		assertTrue(toolbar.getVisible());

	}

	@Test
	public void testCreateGuiBug301021() throws Exception {
