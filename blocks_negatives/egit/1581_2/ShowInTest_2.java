		try {
			ContextMenuHelper.clickContextMenu(projectExplorerTree, "Team",
					menuString);
			fail("This should have failed");
		} catch (SWTException e) {
			assertTrue(e.getCause() instanceof IllegalStateException);
		}
