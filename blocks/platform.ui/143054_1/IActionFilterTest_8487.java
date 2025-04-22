		filter.clearCalled();
		view.selectElement(redTrue);
		menuMgr.getMenu().notifyListeners(SWT.Show, new Event());
		assertTrue(filter.getCalled());
		assertNotNull(ActionUtil.getActionWithLabel(menuMgr, "redAction_v1"));
		assertNull(ActionUtil.getActionWithLabel(menuMgr, "blueAction_v1"));
		assertNotNull(ActionUtil.getActionWithLabel(menuMgr, "trueAction_v1"));
		assertNull(ActionUtil.getActionWithLabel(menuMgr, "falseAction_v1"));
		assertNotNull(ActionUtil
				.getActionWithLabel(menuMgr, "redTrueAction_v1"));
