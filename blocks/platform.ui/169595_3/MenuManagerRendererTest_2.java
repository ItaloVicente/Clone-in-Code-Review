	@Test
	public void testMMenu_Opaque() {
		MenuManager manager = new MenuManager();
		manager.setRemoveAllWhenShown(true);
		MMenu opaqueMenu = OpaqueElementUtil.createOpaqueMenu();
		opaqueMenu.setElementId(manager.getId());
		OpaqueElementUtil.setOpaqueItem(opaqueMenu, manager);
		menu.getChildren().add(opaqueMenu);

		contextRule.createAndRunWorkbench(window);
		opaqueMenu.setVisible(false);
		assertFalse(manager.isVisible());
		opaqueMenu.setVisible(true);
		assertTrue(manager.isVisible());
	}

