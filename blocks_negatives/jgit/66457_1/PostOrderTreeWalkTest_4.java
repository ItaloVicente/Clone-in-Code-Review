		assertModes("b", TREE, tw);
		assertTrue(tw.isSubtree());
		assertFalse(tw.isPostChildren());
		tw.enterSubtree();
		assertModes("b/c", REGULAR_FILE, tw);
		assertModes("b/d", REGULAR_FILE, tw);
