		TreeWalk treeWalk = new TreeWalk(repository);
		treeWalk.addTree(secondCommit.getTree().getId());
		treeWalk.setRecursive(true);
		treeWalk.setPostOrderTraversal(true);
		assertTrue(treeWalk.next());
		assertEquals("sub1/a.txt", treeWalk.getPathString());
		assertTrue(treeWalk.next());
		assertEquals("sub1", treeWalk.getPathString());
		assertTrue(treeWalk.next());
		assertEquals("sub2/b.txt", treeWalk.getPathString());
		assertTrue(treeWalk.next());
		assertEquals("sub2", treeWalk.getPathString());
		assertFalse(treeWalk.next());

