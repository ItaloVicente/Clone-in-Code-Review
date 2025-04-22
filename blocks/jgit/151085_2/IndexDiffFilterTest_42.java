		try (TreeWalk treeWalk = createTreeWalk(commit)) {
			assertTrue(treeWalk.next());
			assertEquals("folder"
			assertTrue(treeWalk.next());
			assertEquals("folder/file"
			assertFalse(treeWalk.next());
		}
