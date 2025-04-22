		TreeWalk treeWalk = createNonRecursiveTreeWalk(commit);

		assertTrue(treeWalk.next());
		assertEquals("folder", treeWalk.getPathString());
		assertTrue(treeWalk.next());
		assertEquals("folder", treeWalk.getPathString());
		assertTrue(treeWalk.isSubtree());
		treeWalk.enterSubtree();
		assertTrue(treeWalk.next());
		assertEquals("folder/file", treeWalk.getPathString());
		assertFalse(treeWalk.next());
