		try (final TreeWalk tw = new TreeWalk(db)) {
			assertFalse(tw.isPostOrderTraversal());
			tw.setPostOrderTraversal(true);
			assertTrue(tw.isPostOrderTraversal());
			tw.setPostOrderTraversal(false);
			assertFalse(tw.isPostOrderTraversal());
		}
