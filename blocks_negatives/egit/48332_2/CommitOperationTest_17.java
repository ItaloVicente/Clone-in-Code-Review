		TreeWalk treeWalk = new TreeWalk(repository);
		treeWalk.addTree(repository.resolve("HEAD^{tree}"));
		assertTrue(treeWalk.next());
		assertEquals("foo", treeWalk.getPathString());
		treeWalk.enterSubtree();
		assertTrue(treeWalk.next());
		assertEquals("foo/a.txt", treeWalk.getPathString());
		assertFalse(treeWalk.next());
