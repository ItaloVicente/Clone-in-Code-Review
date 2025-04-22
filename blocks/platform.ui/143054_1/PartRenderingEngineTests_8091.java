	public void testPartStack_ViewMenuHidenWhenPartsClosed_Bug377228() throws Exception {
		MWindow window = ems.createModelElement(MWindow.class);
		application.getChildren().add(window);

		MPartStack stack = ems.createModelElement(MPartStack.class);
		window.getChildren().add(stack);

		MPart part = ems.createModelElement(MPart.class);
		part.setContributionURI("bundleclass://org.eclipse.e4.ui.tests/org.eclipse.e4.ui.tests.workbench.SampleView");
		stack.getChildren().add(part);

		MMenu menu = ems.createModelElement(MMenu.class);
		menu.getTags().add(StackRenderer.TAG_VIEW_MENU);
		part.getMenus().add(menu);

		MDirectMenuItem item = ems.createModelElement(MDirectMenuItem.class);
		menu.getChildren().add(item);

		contextRule.createAndRunWorkbench(window);

		CTabFolder folder = (CTabFolder) stack.getWidget();
		Composite compA = (Composite) folder.getTopRight();
		ToolBar toolbar = null;
		for (Control child : compA.getChildren()) {
			if (child.getData().equals(StackRenderer.TAG_VIEW_MENU)) {
				toolbar = (ToolBar) child;
			}
		}
		assertTrue(toolbar != null);

		assertTrue(toolbar.getVisible());

		EPartService partService = window.getContext().get(EPartService.class);
		partService.hidePart(part, true);
		contextRule.spinEventLoop();

		assertFalse(toolbar.getVisible());
	}

	@Test
	public void testPartStack_ViewMenuShowWhenItemsAdded_Bug385083() throws Exception {
		MWindow window = ems.createModelElement(MWindow.class);
		application.getChildren().add(window);

		MPartStack stack = ems.createModelElement(MPartStack.class);
		window.getChildren().add(stack);

		MPart part = ems.createModelElement(MPart.class);
		part.setContributionURI("bundleclass://org.eclipse.e4.ui.tests/org.eclipse.e4.ui.tests.workbench.SampleView");
		stack.getChildren().add(part);

		MMenu menu = ems.createModelElement(MMenu.class);
		menu.getTags().add(StackRenderer.TAG_VIEW_MENU);
		part.getMenus().add(menu);

		contextRule.createAndRunWorkbench(window);
