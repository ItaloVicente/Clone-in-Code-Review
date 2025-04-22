	@Test
	public void testDynamicItem_Reconcile_Action_Multiple() {
		contextRule.createAndRunWorkbench(window);
		ToolBarManagerRenderer renderer = getToolBarManagerRenderer();
		ToolBarManager tbm = getToolBarManager();

		assertEquals(0, toolBar.getChildren().size());
		assertEquals(0, tbm.getSize());

		Action action = new Action("Dummy") {
		};

		tbm.add(action);
		tbm.add(action);

		assertEquals(2, tbm.getSize());

		renderer.reconcileManagerToModel(tbm, toolBar);

		assertEquals(2, toolBar.getChildren().size());
	}

