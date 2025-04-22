
		try (TreeWalk treeWalk = createNonRecursiveTreeWalk(commit)) {
			assertTrue(treeWalk.next());
			assertEquals("folder"
			assertTrue(treeWalk.next());
			assertEquals("folder"
			assertTrue(treeWalk.isSubtree());
			treeWalk.enterSubtree();
			assertTrue(treeWalk.next());
			assertEquals("folder/file"
			assertFalse(treeWalk.next());
		}
