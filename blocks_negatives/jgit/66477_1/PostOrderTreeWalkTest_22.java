		final TreeWalk tw = new TreeWalk(db);
		tw.setPostOrderTraversal(true);
		assertTrue(tw.isPostOrderTraversal());
		tw.reset();
		assertTrue(tw.isPostOrderTraversal());

		tw.setPostOrderTraversal(false);
		assertFalse(tw.isPostOrderTraversal());
		tw.reset();
		assertFalse(tw.isPostOrderTraversal());
