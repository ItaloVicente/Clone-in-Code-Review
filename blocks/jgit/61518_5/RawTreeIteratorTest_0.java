
	@Test
	public void testSubtreeDepth2() {
		TreeFormatter tree = new TreeFormatter();
		tree.append("src"
		RawTreeIterator root = new RawTreeIterator(tree.toByteArray());
		assertSame(root
		assertTrue(root.isTree());

		tree = new TreeFormatter();
		tree.append("main"
		RawTreeIterator src = enter(root
		assertSame(src
		assertTrue(src.isTree());

		byte[] path = new byte[2 + src.getPathLength()];
		path[0] = 'A';
		path[1] = 'B';
		src.copyPath(path
		assertEquals("ABsrc/main"

		assertSame(root
		assertSame(root
	}

	@Test
	public void testSubtreeDepth3() {
		TreeFormatter tree = new TreeFormatter();
		tree.append("src"
		RawTreeIterator root = new RawTreeIterator(tree.toByteArray());
		assertSame(root
		assertTrue(root.isTree());

		tree = new TreeFormatter();
		tree.append("main"
		RawTreeIterator src = enter(root
		assertSame(src
		assertTrue(src.isTree());

		tree = new TreeFormatter();
		tree.append("foo.c"
		RawTreeIterator main = enter(src
		assertSame(main
		assertEquals("foo.c"

		byte[] path = new byte[2 + main.getPathLength()];
		path[0] = 'A';
		path[1] = 'B';
		main.copyPath(path
		assertEquals("ABsrc/main/foo.c"

		assertSame(src
		assertSame(root
		assertSame(root
	}

	private static RawTreeIterator enter(RawTreeIterator parent
			TreeFormatter tree) {
		return new RawTreeIterator.Subtree(parent
	}
