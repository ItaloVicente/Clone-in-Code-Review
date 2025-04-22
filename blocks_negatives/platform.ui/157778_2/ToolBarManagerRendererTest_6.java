	@Test
	public void testDynamicItem_Visibility() {
		MToolItem toolItem1 = ems.createModelElement(MDirectToolItem.class);
		toolBar.getChildren().add(toolItem1);

		MToolItem toolItem2 = ems.createModelElement(MDirectToolItem.class);
		toolItem2.setVisible(false);
		toolBar.getChildren().add(toolItem2);

		contextRule.createAndRunWorkbench(window);
		ToolBarManager tbm = getToolBarManager();

		assertEquals(2, tbm.getSize());
		assertTrue(tbm.getItems()[0].isVisible());
		assertFalse(tbm.getItems()[1].isVisible());

		toolItem1.setVisible(false);

		assertEquals(2, tbm.getSize());
		assertFalse(tbm.getItems()[0].isVisible());
		assertFalse(tbm.getItems()[1].isVisible());

		toolItem1.setVisible(true);

		assertEquals(2, tbm.getSize());
		assertTrue(tbm.getItems()[0].isVisible());
		assertFalse(tbm.getItems()[1].isVisible());
	}

